package data;

import data.description.Data;
import data.description.DataTypes;
import data.description.FieldAnnotation;

import java.io.Serializable;
import java.util.Objects;

public class MusicBand extends Data implements Comparable<MusicBand>, Serializable {
    /**
     * Поле не может быть null, Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    @FieldAnnotation(name = "id", ColumnName = "id", DBSets = true)
    private Integer id;
    /**
     * id создателя объекта (только у создателя есть права на редактирование и удаление)
     */
    @FieldAnnotation(name = "id создателя объекта", ColumnName = "creator_id", serverSets = true)
    private int creatorId;
    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    @FieldAnnotation(name = "название группы", ColumnName = "name", nullable = false)
    private String name;
    /**
     * Поле не может быть null
     */
    @FieldAnnotation(name = "координаты", ColumnName = "coordinates", nullable = false, isCompositeDataType = true, compositeDataType = DataTypes.COORDINATES)
    private Coordinates coordinates;
    /**
     * Поле не может быть null,
     * Значение этого поля должно генерироваться автоматически
     */
    @FieldAnnotation(name = "дата добавления в базу", ColumnName = "creation_date", DBSets = true)
    private java.util.Date creationDate;
    /**
     * Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "количество участников", ColumnName = "number_of_participants", isValidate = true)
    private Long numberOfParticipants;
    /**
     * Значение поля должно быть больше 0
     */
    @FieldAnnotation(name = "количество песен", ColumnName = "singles_count", nullable = false, isValidate = true)
    private long singlesCount;
    /**
     * Поле может быть null
     */
    @FieldAnnotation(name = "описание", ColumnName = "description")
    private String description;
    /**
     * Поле не может быть null
     */
    @FieldAnnotation(name = "музыкальный жанр", ColumnName = "genre", nullable = false, isEnum = true)
    private MusicGenre genre;
    /**
     * Поле может быть null
     */
    @FieldAnnotation(name = "лучший альбом", ColumnName = "best_album", isCompositeDataType = true, compositeDataType = DataTypes.ALBUM)
    private Album bestAlbum;

    public MusicGenre getGenre() {
        return this.genre;
    }

    public String getDescription() {
        return description;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(MusicBand o) { // Должен вернуть 1, если this > o
        if (this.bestAlbum != null && o.bestAlbum != null) {
            long thisSales = this.bestAlbum.getSales();
            long thatSales = o.bestAlbum.getSales();
            if (!(thisSales == thatSales))
                return Long.compare(thisSales, thatSales);
        }
        if (this.bestAlbum == null) return -1;
        if (o.bestAlbum == null) return 1;
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

    @Override
    public Integer getId() {
        return this.id;
    }

    public Integer getCreatorId(){
        return this.creatorId;
    }
}
