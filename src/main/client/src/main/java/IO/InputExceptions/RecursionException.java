package IO.InputExceptions;

public class RecursionException extends Exception{
    @Override
    public String toString(){
        return "Достигнута максимальная глубина рекурсивного выполнения скриптов (10)";
    }
}