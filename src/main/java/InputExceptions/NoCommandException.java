package InputExceptions;

public class NoCommandException extends InputException {

    @Override
    public String toString() {
        return "Такой команды нет";
    }
}
