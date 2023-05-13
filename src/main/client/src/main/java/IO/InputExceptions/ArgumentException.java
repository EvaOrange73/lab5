package IO.InputExceptions;

public class ArgumentException {
    private final String argument;
    private final String type;

    public ArgumentException(String argument, String type) {
        this.argument = argument;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Аргумент \"" + this.argument + "\" должен быть типа \"" + this.type + "\"";
    }
}
