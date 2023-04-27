package inputExceptions;

public class TypeOfArgumentException extends InputException {
    private String argument;
    private String type;

    public TypeOfArgumentException(String argument, String type) {
        this.argument = argument;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Аргумент \"" + this.argument + "\" должен быть типа \"" + this.type + "\"";
    }
}
