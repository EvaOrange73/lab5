package data.description;

import data.Album;
import data.Coordinates;
import data.MusicBand;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Типы данных - хранит подклассы Data, которые умеет обрабатывать DataFactory
 */
public enum DataTypes { //TODO может сделать внутренним классом Data?
    MUSIC_BAND("data.MusicBand", MusicBand.class),
    ALBUM("data.Album", Album.class),
    COORDINATES("data.Coordinates", Coordinates.class);

    private final String typeName;
    private final Class<? extends Data> clazz;

    DataTypes(String typeName, Class<? extends Data> clazz) {
        this.typeName = typeName;
        this.clazz = clazz;
    }

    /**
     * @return список полей
     */
    public Field[] getDeclaredFields() {
        return this.clazz.getDeclaredFields();
    }

    /**
     * @return пустой объект
     */
    public Data getNewInstance() {
        try {
            return this.clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException("Не удалось создать пустой объект Data");
        }
    }

    /**
     * Пустой объект по имени класса
     * @param name имя класса
     * @return пустой объект
     */
    public static Data getNewInstanceByName(String name) {
        for (DataTypes dataTypes : DataTypes.values())
            if (Objects.equals(dataTypes.typeName, name)) {
                try {
                    return dataTypes.clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        throw new RuntimeException("Не удалось создать пустой объект Data");
    }
}
