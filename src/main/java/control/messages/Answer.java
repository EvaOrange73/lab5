package control.messages;

import java.util.HashMap;

/**
 * Формат, в котором поля, введенные пользователем, передаются из IOManager в DataFactory
 */
public class Answer {
    private String answer;
    private boolean isComposite;
    private HashMap<Integer, Answer> subAnswers = new HashMap<>();

    public Answer(String answer, boolean isComposite){
        this.answer = answer;
        this.isComposite = isComposite;
    }

    public String getAnswer() {
        return answer;
    }

    public void addSubAnswers(HashMap<Integer, Answer> subAnswers){
        this.subAnswers = subAnswers;
    }

    public HashMap<Integer, Answer> getSubAnswers() {
        return subAnswers;
    }

    @Override
    public String toString(){
        return answer;
    }
}