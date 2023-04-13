package DataDescription;

import InputExceptions.FieldException;
import InputExceptions.TypeOfFieldException;

public class Album {
    /**
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Поле может быть null, Значение поля должно быть больше 0
     */
    private Integer tracks;
    /**
     * Значение поля должно быть больше 0
     */
    private long length;
    /**
     * Значение поля должно быть больше 0
     */
    private long sales;

    public Album(String[] args) throws FieldException {
        String[] fields = getFields();
        if (args[0] != null && !args[0].isEmpty()) this.name = args[0];
        else throw new FieldException(fields[0], "Строка не может быть пустой", 0);

        if (args[1].isEmpty()) this.tracks = null;
        else {
            try {
                int tracks = Integer.parseInt(args[1]);
                if (tracks > 0) this.tracks = tracks;
                else throw new FieldException(fields[1], "Значение поля должно быть больше 0", 1);
            } catch (NumberFormatException e) {
                throw new TypeOfFieldException("name", "Integer", 1);
            }
        }

        this.length = parsePositiveLong(args[2], "lenght", 2);
        this.sales = parsePositiveLong(args[3], "sales", 3);
    }


    public static String[] getFields() {
        return new String[]{"name", "tracks", "lenght", "sales"};
    }

    private long parsePositiveLong(String arg, String fieldName, int number) throws FieldException {
        if (arg.isEmpty()) throw new FieldException(fieldName, "Поле не может быть null", number);
        try {
            long positiveLong = Long.parseLong(arg);
            if (positiveLong > 0) return positiveLong;
            else throw new FieldException(fieldName, "Значение поля должно быть больше 0", number);
        } catch (NumberFormatException e) {
            throw new TypeOfFieldException(fieldName, "Long", number);
        }
    }

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
    public String toString() {
        return "Альбом: \n              " +
                "name: " + this.name + "\n              " +
                "tracks: " + this.tracks + "\n              " +
                "length: " + this.length + "\n              " +
                "sales: " + this.sales + "\n";
    }
}
