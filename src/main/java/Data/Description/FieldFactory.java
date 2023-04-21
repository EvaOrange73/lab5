package Data.Description;

import Control.Messages.Answer;
import Control.Messages.Question;
import Data.Generation.Generator;
import Data.MusicGenre;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class FieldFactory {
    private DataDescription object;
    private int number;
    private Field field;
    private FieldAnnotation annotation;

    private ArrayList<FieldFactory> subfields;

    private Question question;
    private String input;

    private boolean isSet = false;
    private String exceptionMessage;

    public FieldFactory(Field field, int number, DataDescription object) {
        this.object = object;
        this.number = number;
        this.field = field;
        this.annotation = field.getAnnotation(FieldAnnotation.class);

        if (this.annotation.isCompositeDataType()) {
            DataDescription compositeObject = DataTypes.getNewInstanceByName(field.getType().getName());
            setField(compositeObject);
            isSet = false;
            this.subfields = setSubfields(compositeObject);
        }

        if (this.annotation.isGenerate()) setField(generate());
        else this.question = new Question(this.annotation);
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setInput(Answer answer) {
        this.input = answer.getAnswer();
        if (this.annotation.isCompositeDataType()) {
            if (answer.getSubAnswers().isEmpty())
                setField(null);
            else {
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
        } else {
            setFieldOrException();
        }
    }

    public FieldAnnotation getAnnotation() {
        return annotation;
    }

    public ArrayList<FieldFactory> getSubfields() {
        return subfields;
    }

    public int getNumber() {
        return number;
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

    private ArrayList<FieldFactory> setSubfields(DataDescription compositeObject) {
        ArrayList<FieldFactory> subfields = new ArrayList<>();
        int i = 0;
        for (Field subfield : compositeObject.getClass().getDeclaredFields()) {
            subfields.add(new FieldFactory(subfield, i, compositeObject));
            i++;
        }
        return subfields;
    }

    private void setFieldOrException() {
        if (this.input.isEmpty()) {
            if (this.annotation.nullable()) setField(null);
            else this.setException("Поле не может быть null");
            return;
        }
        if (this.annotation.isCompositeDataType()) {
            //TOdO exceptions
            return;
        }
        this.exceptionMessage = null;
        Object inputWithType = setTypeOrException();
        if (this.exceptionMessage == null && inputWithType != null)
            if (this.annotation.isValidate()) {
                validateOrException(inputWithType);
                if (this.exceptionMessage == null) //TODO может стоит использовать настоящие ошибки?
                    setField(inputWithType);
            } else setField(inputWithType);
    }

    private void setField(Object value) {
        this.isSet = true;
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Что-то пошло не так при установке значения поля\n" + e);
        }
    }

    private void setException(String exceptionMessage) {
        exceptionMessage = "Ошибка в поле " + this.annotation.name() + ": " + exceptionMessage;
        this.exceptionMessage = exceptionMessage;
        this.question.setExceptionMessage(exceptionMessage);
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
            this.setException("Значение поля должно быть типа" + field.getType());
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
}
