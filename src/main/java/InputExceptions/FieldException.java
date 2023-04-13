package InputExceptions;

public class FieldException extends InputException{
    String description;
    int number;

    public FieldException(String fieldName, String description, int number){
        this.description = "Ошибка в поле " + fieldName + " : " + description;
        this.number = number;
    }
    @Override
    public String toString() {
        return description;
    }
    public int getFieldNumber(){
        return this.number;
    }
}
