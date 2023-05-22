package data;

import data.description.Data;
import data.description.FieldAnnotation;

import java.io.Serializable;

public class Coordinates extends Data implements Serializable {
    /**
     * Поле не может быть null
     */
    @FieldAnnotation(name = "x", nullable = false)
    private Integer x;
    /**
     * Значение поля должно быть больше -473, Поле не может быть null
     */
    @FieldAnnotation(name = "y", nullable = false, isValidate = true, moreThen = -473)
    private Float y; //

    @Override
    public String toString() {
        return super.toString("Координаты");
    }

    public double getDistance() {
        return Math.sqrt(x * x + y * y);
    }
}
