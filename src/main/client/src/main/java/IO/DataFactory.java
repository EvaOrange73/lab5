package IO;

import data.description.Data;
import data.description.DataTypes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Фабрика подклассов Data
 */
public class DataFactory {

    final Data object;
    final int userId;
    private final HashMap<Integer, FieldFactory> fields;

    public DataFactory(DataTypes dataType, int userId) {
        this.object = dataType.getNewInstance();
        this.userId = userId;
        this.fields = formFields(dataType);
    }

    private HashMap<Integer, FieldFactory> formFields(DataTypes dataType) {
        HashMap<Integer, FieldFactory> fieldsMap = new HashMap<>();
        int i = 0;
        for (Field field : dataType.getDeclaredFields()) {
            fieldsMap.put(i, new FieldFactory(field, i, object, userId));
            i++;
        }
        return fieldsMap;
    }

    /**
     * @return список вопросов, которые нужно задать пользователю
     */
    public HashMap<Integer, Question> getQuestions() {
        HashMap<Integer, Question> questions = new HashMap<>();
        this.fields.forEach((key, field) -> {
            Question question = field.getQuestion();
            if (field.isComposite()) {
                ArrayList<FieldFactory> unsetSubFields = new ArrayList<>();
                for (FieldFactory subfield : field.getSubfields()) {
                    if (!(subfield.isSet())) unsetSubFields.add(subfield);
                }
                question.setSubQuestions(unsetSubFields);
            }
            if (!(field.isSet()) && question != null)
                questions.put(key, question);
        });
        return questions;
    }

    /**
     * Устанавливает поля по ответам пользователя
     *
     * @param answers ответы пользователя. Ключи соответствуют ключам вопросов, на которые пользователь отвечал
     */
    public void setAnswers(HashMap<Integer, Answer> answers) {
        answers.forEach((key, answer) ->
                this.fields.get(key).setInput(answer)
        );
    }

    /**
     * @return остались ли неустановленные поля?
     */
    public boolean hasUnsetFields() {
        for (FieldFactory field : this.fields.values())
            if (!(field.isSet())) return true;
        return false;
    }

    public String getExceptionsList() {
        StringBuilder exceptions = new StringBuilder();
        for (FieldFactory field : fields.values()) {
            if (!(field.isSet()))
                exceptions.append("\n").append(field.getExceptionMessage());
        }
        return exceptions.toString();
    }

    /**
     * @return Сформированный фабрикой подкласс Data
     */
    public Data getFormedObject() {
        return object;
    }
}
