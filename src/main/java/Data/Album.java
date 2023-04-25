package Data;

import Data.Description.Data;
import Data.Description.FieldAnnotation;

public class Album extends Data {
    /**
     * Поле не может быть null, Строка не может быть пустой
     */
    @FieldAnnotation(name = "название альбома", nullable = false)
    private String name;

    /**
     * Поле может быть null, Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "количество треков", isValidate = true)
    private Integer tracks;
    /**
     * Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "длина альбома", nullable = false, isValidate = true)
    private long length;
    /**
     * Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "количество проданных копий", nullable = false, isValidate = true)
    private long sales;

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
    public String toString() {
        return super.toString("Альбом");
    }

    public long getSales() {
        return sales;
    }
}
