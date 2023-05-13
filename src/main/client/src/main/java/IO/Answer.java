package IO;

import java.util.HashMap;

/**
 * Формат, в котором поля, введенные пользователем, передаются из messages.IOManager в DataFactory
 */
public class Answer {
    private final String answer;
    private HashMap<Integer, Answer> subAnswers = new HashMap<>();

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void addSubAnswers(HashMap<Integer, Answer> subAnswers) {
        this.subAnswers = subAnswers;
    }

    public HashMap<Integer, Answer> getSubAnswers() {
        return subAnswers;
    }

    @Override
    public String toString() {
        return answer;
    }
}
