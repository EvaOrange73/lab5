package Control.Messages;

import Data.Description.FieldAnnotation;
import Data.Description.FieldFactory;
import Data.MusicGenre;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Формат, в котором передаются приглашения ко вводу недостающих полей от DataFactory к IOManager
 */
public class Question {
    private FieldAnnotation annotation;

    private String question = "";
    private String exceptionMessage = "";

    private boolean isComposite = false;
    private HashMap<Integer, Question> subQuestions = new HashMap<>();

    public Question(FieldAnnotation annotation) {
        this.annotation = annotation;
        getQuestion();
    }


    public String getQuestion() {
        this.question = this.exceptionMessage;
        if (annotation.isEnum()) {
            this.question += "Выберете " + annotation.name() + ":";
            for (int j = 0; j < MusicGenre.values().length; j++) {
                this.question += "\n" + (j + 1) + ": " + MusicGenre.values()[j];
            }
        } else if (annotation.isCompositeDataType()) {
            this.isComposite = true;
            this.question += "Вы хотите ввести " + annotation.name() + "? y/n";

        } else this.question += "Введите " + annotation.name();
        return this.question;
    }

    public boolean isComposite() {
        return isComposite;
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
