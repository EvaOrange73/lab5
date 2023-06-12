package data.description;

import data.Album;
import data.Coordinates;
import data.MusicBand;
import data.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Типы данных - хранит подклассы Data, которые умеет обрабатывать DataFactory
 */
public enum DataTypes { // может сделать внутренним классом Data?
    MUSIC_BAND("Музыкальная группа", "data.MusicBand", MusicBand.class, "music_bands"),
    ALBUM("Альбом", "data.Album", Album.class, "albums"),
    COORDINATES("Координаты","data.Coordinates", Coordinates.class, "coordinates"),
    USER("Пользователь", "data.User", User.class, "users"),
    DEFAULT("", "", null, "");

    private final String name;
    private final String typeName;
    private final Class<? extends Data> clazz;
    private final String DBname;

    DataTypes(String name, String typeName, Class<? extends Data> clazz, String DBname) {
        this.name = name;
        this.typeName = typeName;
        this.clazz = clazz;
        this.DBname = DBname;
    }

    public static DataTypes getTypeByName(String name){
        for (DataTypes dataTypes : DataTypes.values())
            if (Objects.equals(dataTypes.typeName, name)) {
                return dataTypes;
            }
        throw new RuntimeException("Такого типа данных нет в DataTypes");
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

    public String getName() {
        return name;
    }

    public String getTableName(){
        return this.DBname;
    }
}
