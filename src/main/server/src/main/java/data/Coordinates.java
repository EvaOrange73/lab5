package data;

import data.description.Data;
import data.description.FieldAnnotation;

public class Coordinates extends Data {
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
}
