package DataDescription;

import InputExceptions.FieldException;
import InputExceptions.TypeOfFieldException;

import java.util.Date;
import java.util.Objects;

public class MusicBand implements Comparable<MusicBand> {
    /**
     * Поле не может быть null, Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    private Integer id;
    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    private String name;
    /**
     * Поле не может быть null
     */
    private Coordinates coordinates;
    /**
     * Поле не может быть null,
     * Значение этого поля должно генерироваться автоматически
     */
    private java.util.Date creationDate;
    /**
     * Значение поля должно быть больше 0
     */
    private Long numberOfParticipants;
    /**
     * Значение поля должно быть больше 0
     */
    private long singlesCount;
    /**
     * Поле может быть null
     */
    private String description;
    /**
     * Поле может быть null
     */
    private MusicGenre genre;
    /**
     * Поле может быть null
     */
    private Album bestAlbum;

    private static Integer nextId = 1;

    public MusicBand(String[] args, Coordinates coordinates, MusicGenre genre, Album album) throws FieldException {
        this.id = nextId;
        nextId++;
        if (args[0] != null && !args[0].isEmpty()) this.name = args[0];
        else throw new FieldException("name", "Строка не может быть пустой", 0);
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.numberOfParticipants = parsePositiveLong(args[1], "numberOfParticipants", 1);
        this.singlesCount = parsePositiveLong(args[2], "singlesCount", 2);
        if (args[3].isEmpty()) this.description = null;
        else this.description = args[3];
        this.genre = genre;
        this.bestAlbum = album;
    }

    public MusicBand(String[] args) throws FieldException { //TODO
        this.id = nextId;
        nextId++;
        if (args[0] != null && !args[0].isEmpty()) this.name = args[0];
        else throw new FieldException("name", "Строка не может быть пустой", 0);
        this.coordinates = new Coordinates(new String[]{args[1], args[2]});
        this.creationDate = new Date();
        this.numberOfParticipants = parsePositiveLong(args[3], "numberOfParticipants", 1);
        this.singlesCount = parsePositiveLong(args[4], "singlesCount", 2);
        if (args[5].isEmpty())
            this.description = null;
        else
            this.description = args[5];
        if(args[6].isEmpty()) throw new FieldException("genre", "Жанр не может быть null", 6);
        this.genre = MusicGenre.values()[Integer.parseInt(args[6]) - 1];
        if (args[7].isEmpty())
            this.bestAlbum = null;
        else
            this.bestAlbum = new Album(new String[]{args[7], args[8], args[9], args[10]});
    }

    private long parsePositiveLong(String arg, String fieldName, int number) throws FieldException {
        try {
            long positiveLong = Long.parseLong(arg);
            if (positiveLong > 0) return positiveLong;
            else throw new FieldException(fieldName, "Значение поля должно быть больше 0", number);
        } catch (NumberFormatException e) {
            throw new TypeOfFieldException(fieldName, "Long", number);
        }
    }

    @Override
    public String toString() {
        return "Музыкальная группа: \n" +
                "id: " + this.id + "\n" +
                "name: " + this.name + "\n" +
                "coordinates: " + this.coordinates.toString() + "\n" +
                "creationDate: " + this.creationDate + "\n" +
                "numberOfParticipants: " + this.numberOfParticipants + "\n" +
                "singlesCount: " + this.singlesCount + "\n" +
                "description: " + this.description + "\n" +
                "genre: " + this.genre + "\n" +
                "bestAlbum: " + this.bestAlbum.toString()
                ;
    }

    public MusicGenre getGenre() {
        return this.genre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setNextId(int id) {
        nextId = id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(MusicBand o) {
        long thisSales = this.bestAlbum.getSales();
        long thatSales = o.bestAlbum.getSales();
        if (!(thisSales == thatSales))
            return Long.compare(thisSales, thatSales);
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
