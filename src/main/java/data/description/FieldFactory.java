package data.description;

import control.messages.Answer;
import control.messages.Question;
import data.generation.Generator;
import data.MusicGenre;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Фабрика полей - класс, осуществляющий генерацию/валидацию значения поля
 * и последующую установку ошибки или значения поля в объекте Data
 */
public class FieldFactory {
    private final Data object;
    private final int number;
    private final Field field;
    private final FieldAnnotation annotation;

    private boolean isComposite = false;
    private ArrayList<FieldFactory> subfields = new ArrayList<>();

    private Question question;
    private String input;

    private boolean isSet = false;
    private String exceptionMessage;

    /**
     * Конструктор
     * Если поле - объект составного типа, создаст FieldFactory для каждого поля этого типа
     * Если поле генерируемо, вызовет соответствующий генератор
     *
     * @param field  - поле, которое нужно установить
     * @param number - номер поля. Будет совпадать с номером соответствующих вопросов и ответов
     * @param object - объект, которому нужно установить это поле
     */
    public FieldFactory(Field field, int number, Data object) {
        this.object = object;
        this.number = number;
        this.field = field;
        this.annotation = field.getAnnotation(FieldAnnotation.class);

        if (this.annotation.isCompositeDataType()) {
            this.isComposite = true;
            Data compositeObject = DataTypes.getNewInstanceByName(field.getType().getName());
            setField(compositeObject);
            isSet = false;
            this.subfields = generateSubfields(compositeObject);
        }

        if (this.annotation.isGenerate()) setField(generate());
        else this.question = new Question(this.annotation);
    }

    private Object generate() {
        Generator generator;
        try {
            generator = this.annotation.generator().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Что-то пошло не так при генерации поля " + this.annotation.name() + "\n" + e);
        }
        return generator.generate();
    }

    private ArrayList<FieldFactory> generateSubfields(Data compositeObject) {
        ArrayList<FieldFactory> subfields = new ArrayList<>();
        int i = 0;
        for (Field subfield : compositeObject.getClass().getDeclaredFields()) {
            subfields.add(new FieldFactory(subfield, i, compositeObject));
            i++;
        }
        return subfields;
    }

    /**
     * @return номер поля
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return Получилось ли установить поле
     */
    public boolean isSet() {
        return isSet;
    }

    /**
     * @return вопрос, который нужно задать пользователю (приглашение ко вводу + предыдущая ошибка, если есть)
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * @return Сообщение об ошибках, возникших в поле
     * и его подполях, если поле - объект составного типа
     */
    public String getExceptionMessage() {
        String exception = this.exceptionMessage;
        for (FieldFactory subfield : subfields) {
            if (!(subfield.isSet) && subfield.getExceptionMessage() != null)
                exception += "\n    " + subfield.getExceptionMessage();
        }
        return exception;
    }

    /**
     * @return поле -- объект составного типа?
     */
    public boolean isComposite() {
        return isComposite;
    }

    /**
     * @return список полей объекта составного типа
     */
    public ArrayList<FieldFactory> getSubfields() {
        return subfields;
    }

    /**
     * Установить введенное значение
     *
     * @param answer значение, которое пользователь хочет установить в эо поле
     */
    public void setInput(Answer answer) {
        this.input = answer.getAnswer();
        if (this.annotation.isCompositeDataType()) {
            setCompositeFieldOrException(answer);
        } else {
            setSimpleFieldOrException();
        }
    }

    private void setCompositeFieldOrException(Answer answer) {
        if (answer.getSubAnswers().isEmpty()) {
            setField(null);
        } else {
            boolean allSubfieldsSet = true;
            for (FieldFactory subfield : this.subfields) {
                subfield.setInput(answer.getSubAnswers().get(subfield.number));
                if (!(subfield.isSet)) allSubfieldsSet = false;
            }
            this.isSet = allSubfieldsSet;
            if (!(allSubfieldsSet)) {
                this.exceptionMessage = "При вводе составного типа возникли ошибки в полях";
            }
        }
    }

    private void setSimpleFieldOrException() {
        if (this.input.isEmpty()) {
            setField(null);
            return;
        }
        this.exceptionMessage = null;
        Object inputWithType = setTypeOrException();
        if (this.exceptionMessage == null && inputWithType != null)
            if (this.annotation.isValidate()) {
                validateOrException(inputWithType);
                if (this.exceptionMessage == null)
                    setField(inputWithType);
            } else setField(inputWithType);
    }

    private Object setTypeOrException() {
        try {
            switch (this.field.getType().getTypeName()) {
                case "java.lang.String" -> {
                    return this.input;
                }
                case "int", "java.lang.Integer" -> {
                    return Integer.parseInt(this.input);
                }
                case "long", "java.lang.Long" -> {
                    return Long.parseLong(this.input);
                }
                case "float", "java.lang.Float" -> {
                    return Float.parseFloat(this.input);
                }
                case "Data.MusicGenre" -> {
                    try {
                        return MusicGenre.values()[Integer.parseInt(this.input) - 1];
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        this.setException("Нужно ввести номер из списка");
                    }
                }
                default -> throw new RuntimeException("Что-то пошло не так при преобразовании типов");
            }
        } catch (NumberFormatException e) {
            this.setException("Значение поля должно быть типа " + field.getType());
        }
        return null;
    }

    private void validateOrException(Object input) {
        boolean flag = true;
        int moreThen = this.annotation.moreThen();
        switch (input.getClass().getTypeName()) {
            case "java.lang.Integer" -> {
                if ((int) input < moreThen) flag = false;
            }
            case "long", "java.lang.Long" -> {
                if ((long) input < (long) moreThen) flag = false;
            }
            case "float", "java.lang.Float" -> {
                if ((float) input < (float) moreThen) flag = false;
            }
            default -> throw new RuntimeException("Что-то пошло не так при сравнении значения");
        }
        if (!(flag)) this.setException("Значение должно быть больше " + moreThen);
    }

    private void setField(Object value) {
        if (value == null && !(this.annotation.nullable())) {
            this.setException("Поле не может быть null");
            return;
        }
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Что-то пошло не так при установке значения поля\n" + e);
        }
        this.isSet = true;
    }

    private void setException(String exceptionMessage) {
        exceptionMessage = "Ошибка в поле " + this.annotation.name() + ": " + exceptionMessage;
        this.exceptionMessage = exceptionMessage;
        this.question.setExceptionMessage(exceptionMessage);
    }
}
