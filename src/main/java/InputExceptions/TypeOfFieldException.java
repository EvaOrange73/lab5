package InputExceptions;

public class TypeOfFieldException extends FieldException{
    public TypeOfFieldException(String fieldName, String type, int number) {
        super(
                fieldName,
                "Аргумент должен быть типа " + type,
                number
        );
    }
}
