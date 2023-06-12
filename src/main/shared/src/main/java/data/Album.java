package data;

import data.description.Data;
import data.description.FieldAnnotation;

import java.io.Serializable;

public class Album extends Data implements Serializable {
    /**
     * id в базе данных
     */
    @FieldAnnotation(name = "id", ColumnName = "id", printable = false, DBSets = true)
    private int id;

    /**
     * Поле не может быть null, Строка не может быть пустой
     */
    @FieldAnnotation(name = "название альбома", ColumnName = "name", nullable = false)
    private String name;

    /**
     * Поле может быть null, Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "количество треков", ColumnName = "tracks", isValidate = true)
    private Integer tracks;
    /**
     * Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "длина альбома", ColumnName = "length", nullable = false, isValidate = true)
    private long length;
    /**
     * Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "количество проданных копий", ColumnName = "sales", nullable = false, isValidate = true)
    private long sales;

    public long getSales() {
        return sales;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Album album)) {
            return false;
        }
        return this.name.equals(album.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
