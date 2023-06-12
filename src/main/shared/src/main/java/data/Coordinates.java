package data;

import data.description.Data;
import data.description.FieldAnnotation;

import java.io.Serializable;

public class Coordinates extends Data implements Serializable {
    /**
     * id в базе данных
     */
    @FieldAnnotation(name = "id", ColumnName = "id", printable = false, DBSets = true)
    private int id;
    /**
     * Поле не может быть null
     */
    @FieldAnnotation(name = "x", ColumnName = "x", nullable = false)
    private Integer x;
    /**
     * Значение поля должно быть больше -473, Поле не может быть null
     */
    @FieldAnnotation(name = "y", ColumnName = "y", nullable = false, isValidate = true, moreThen = -473)
    private Float y;

    @Override
    public String toString() {
        return super.toString();
    }

    public double getDistance() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
