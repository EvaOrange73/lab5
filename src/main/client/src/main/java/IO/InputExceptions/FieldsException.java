package IO.InputExceptions;

public class FieldsException extends Exception{
    String text;
    public FieldsException(String exceptions){
        this.text = "Не все поля введены верно" + exceptions;
    }
    public FieldsException(){
        this("Вы слишком много раз неверно вводили поля");
    }
    @Override
    public String toString(){
        return this.text;
    }
}
