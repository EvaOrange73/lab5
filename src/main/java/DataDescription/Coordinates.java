package DataDescription;

import InputExceptions.FieldException;
import InputExceptions.TypeOfFieldException;

public class Coordinates {
    /**
     * Поле не может быть null
     */
    /**
     * Значение поля должно быть больше -473, Поле не может быть null
     */
    private Integer x;
    private Float y; //

    public Coordinates(String[] args) throws FieldException{
        if (args[0].isEmpty()) throw new FieldException("x", "Поле не может быть null", 0);
        try {
            this.x = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            throw new TypeOfFieldException("x", "int", 0);
        }
        try {
            float y = Float.parseFloat(args[1]);
            if (y > -473) this.y = y;
            else throw new FieldException("y", "Значение поля должно быть больше -473", 1);
        } catch (NumberFormatException e){
            throw new TypeOfFieldException("y", "float", 1);
        }
    }

    @Override
    public String toString(){
        return "x = " + this.x + ", y = " + this.y;
    }
}
