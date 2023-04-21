package Data.Description;

import Data.Album;
import Data.Coordinates;
import Data.MusicBand;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public enum DataTypes {
    MUSIC_BAND("Data.MusicBand", MusicBand.class),
    ALBUM("Data.Album", Album.class),
    COORDINATES("Data.Coordinates", Coordinates.class);

    private String typeName;
    private Class<? extends DataDescription> clazz;

    DataTypes(String typeName, Class<? extends DataDescription> clazz) {
        this.typeName = typeName;
        this.clazz = clazz;
    }

    public DataDescription getNewInstance() {
        try {
            return this.clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataDescription getNewInstanceByName(String name) {
        for (DataTypes dataTypes : DataTypes.values())
            if (Objects.equals(dataTypes.typeName, name)) {
                try {
                    return dataTypes.clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        System.out.println("Не нашлось соответствие"); //TODO убрать
        return null;
    }
}
