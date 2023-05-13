package IO;

import data.MusicGenre;
import data.description.FieldAnnotation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Формат, в котором передаются приглашения ко вводу недостающих полей от DataFactory к messages.IOManager
 */
public class Question {

    private String question = "";
    private String exceptionMessage = "";

    private boolean isComposite = false;
    private boolean isNullableComposite = false;
    private HashMap<Integer, Question> subQuestions = new HashMap<>();

    public Question(FieldAnnotation annotation) {
        if (annotation.isEnum()) {
            this.question += "Выберете " + annotation.name() + ":";
            for (int j = 0; j < MusicGenre.values().length; j++) {
                this.question += "\n" + (j + 1) + ": " + MusicGenre.values()[j];
            }
        } else if (annotation.isCompositeDataType()) {
            this.isComposite = true;
            if (annotation.nullable()) {
                this.isNullableComposite = true;
                this.question += "Вы хотите ввести " + annotation.name() + "? y/n";
            } else {
                this.isNullableComposite = false;
                this.question += "Введите " + annotation.name();
            }
        } else this.question += "Введите " + annotation.name();
    }


    public String getQuestion() {
        return this.exceptionMessage + this.question;
    }

    public boolean isComposite() {
        return isComposite;
    }

    public boolean isNullableComposite() {
        return isNullableComposite;
    }

    public HashMap<Integer, Question> getSubQuestions() {
        return subQuestions;
    }

    public void setSubQuestions(ArrayList<FieldFactory> subfields) {
        this.subQuestions = new HashMap<>();
        for (FieldFactory subfield : subfields)
            this.subQuestions.put(subfield.getNumber(), subfield.getQuestion());

    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage + "\n";
    }
}
