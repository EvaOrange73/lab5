package InputExceptions;

public class NoArgumentsException extends InputException{
    private String argument;
    private String type;

    public NoArgumentsException(String argument, String type) {
        this.argument = argument;
        this.type = type;
    }
    @Override
    public String toString() {
        return "У этой команды должен быть аргумент\"" + this.argument + "\" типа \"" + this.type + "\"";
    }
}
