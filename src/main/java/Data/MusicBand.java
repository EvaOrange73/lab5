package Data;

import Data.Description.Data;
import Data.Description.FieldAnnotation;
import Data.Generation.DataGenerator;
import Data.Generation.IdGenerator;

import java.util.Objects;

public class MusicBand extends Data implements Comparable<MusicBand> {
    /**
     * Поле не может быть null, Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    @FieldAnnotation(name = "id", isGenerate = true, generator = IdGenerator.class)
    private Integer id;
    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    @FieldAnnotation(name = "название группы", nullable = false)
    private String name;
    /**
     * Поле не может быть null
     */
    @FieldAnnotation(name = "координаты", nullable = false, isCompositeDataType = true)
    private Coordinates coordinates;
    /**
     * Поле не может быть null,
     * Значение этого поля должно генерироваться автоматически
     */
    @FieldAnnotation(name = "дата добавления в базу", isGenerate = true, generator = DataGenerator.class)
    private java.util.Date creationDate;
    /**
     * Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "количество участников", isValidate = true)
    private Long numberOfParticipants;
    /**
     * Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "количество песен", nullable = false, isValidate = true)
    private long singlesCount;
    /**
     * Поле может быть null
     */
    @FieldAnnotation(name = "описание")
    private String description;
    /**
     * Поле не может быть null
     */
    @FieldAnnotation(name = "музыкальный жанр", nullable = false, isEnum = true)
    private MusicGenre genre;
    /**
     * Поле может быть null
     */
    @FieldAnnotation(name = "лучший альбом", isCompositeDataType = true)
    private Album bestAlbum;

    public MusicGenre getGenre() {
        return this.genre;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return super.toString("Музыкальная группа");
    }

    @Override
    public int compareTo(MusicBand o) {
        if (this.bestAlbum != null) {
            long thisSales = this.bestAlbum.getSales();
            long thatSales = o.bestAlbum.getSales();
            if (!(thisSales == thatSales))
                return Long.compare(thatSales, thisSales);
        }
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof MusicBand)) return false;
        return Objects.equals(this.name, ((MusicBand) that).name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
