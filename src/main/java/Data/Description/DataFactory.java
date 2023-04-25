package Data.Description;

import Control.Messages.Answer;
import Control.Messages.Question;
import Data.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class DataFactory {

    DataDescription object;
    private HashMap<Integer, FieldFactory> fields;

    public DataFactory(DataTypes dataType) {
        this.object = dataType.getNewInstance();
        this.fields = formFields(dataType);
    }

    private HashMap<Integer, FieldFactory> formFields(DataTypes dataType) {
        HashMap<Integer, FieldFactory> fieldsMap = new HashMap<>();
        Field[] fields;
        switch (dataType) {
            case MUSIC_BAND -> fields = MusicBand.class.getDeclaredFields(); //TODO перенести в DataTypes
            case COORDINATES -> fields = Coordinates.class.getDeclaredFields();
            case ALBUM -> fields = Album.class.getDeclaredFields();
            default -> throw new RuntimeException("Что-то пошло не так при формировании списка полей");
        }
        int i = 0;
        for (Field field : fields) {
            fieldsMap.put(i, new FieldFactory(field, i, object));
            i++;
        }
        return fieldsMap;
    }

    public HashMap<Integer, Question> getQuestions() {
        HashMap<Integer, Question> questions = new HashMap<>();
        this.fields.forEach((key, field) -> {
            Question question = field.getQuestion();
            if (field.getAnnotation().isCompositeDataType()){
                ArrayList<FieldFactory> unsetSubFields = new ArrayList<>();
                for (FieldFactory subfield: field.getSubfields()){
                    if (!(subfield.isSet())) unsetSubFields.add(subfield);
                }
                question.setSubQuestions(unsetSubFields);
            }
            if (!(field.isSet()) && question != null)
                questions.put(key, question);
        });
        return questions;
    }

    public void setAnswers(HashMap<Integer, Answer> answers) {
        answers.forEach((key, answer) -> {
            FieldFactory field = this.fields.get(key);
            if (answer != null) {
                field.setInput(answer);
            }
        });
    }

    public boolean hasUnsetFields() {
        for (FieldFactory field : this.fields.values())
            if (!(field.isSet())) return true;
        return false;
    }

    public String getExceptionsList(){
        String exceptions = "";
        for (FieldFactory field: fields.values()) {
            if (!(field.isSet()))
                exceptions += "\n" + field.getExceptionMessage();
        }
        return exceptions;
    }


    public DataDescription getFormedObject() {
        return object;
    }
}
