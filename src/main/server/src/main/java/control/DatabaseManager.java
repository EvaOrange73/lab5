package control;

import data.MusicBand;
import data.MusicGenre;
import data.User;
import data.description.Data;
import data.description.DataTypes;
import data.description.FieldAnnotation;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Properties;

/**
 *
 */
public class DatabaseManager {
    String url = "jdbc:postgresql://localhost:5432/studs";
    Properties properties = new Properties();

    public DatabaseManager() throws IOException {
        properties.load(this.getClass().getResourceAsStream("/db.cfg"));
    }

    public User getUserByName(String username) {
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from users where username = '" + username + "'");
            ArrayList<Data> users = getObjectsFromResultSet(rs, DataTypes.USER);
            if (users.size() == 0) return null;
            return (User) users.get(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeMusicBand(int musicBandId) {
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            PreparedStatement statement = connection.prepareStatement("delete from music_bands where id = ?;");
            statement.setInt(1, musicBandId);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void update(Integer oldId, Data o) {
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            PreparedStatement statement = connection.prepareStatement(o.getUpdateQuery(oldId));
            Field[] fields = o.getClass().getDeclaredFields();
            int i = 0;
            for (Field field : fields) {
                FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
                field.setAccessible(true);
                if (!(annotation.DBSets())) {
                    if (annotation.isCompositeDataType()) {
                        Data composite = (Data) field.get(o);
                        if(composite != null)
                            update(composite.getId(), composite);
                    } else if (annotation.isEnum()) {
                        statement.setObject(i + 1, selectId((MusicGenre) field.get(o)));
                        i++;
                    } else {
                        statement.setObject(i + 1, field.get(o));
                        i++;
                    }
                }
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedHashSet<MusicBand> getCollection() {
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from music_bands");
            ArrayList<Data> musicBands = getObjectsFromResultSet(rs, DataTypes.MUSIC_BAND);
            LinkedHashSet<MusicBand> set = new LinkedHashSet<>();
            for (Data data : musicBands) {
                set.add((MusicBand) data);
            }
            return set;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer insertAndGetId(Data o) {
        if (o == null) return null;
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            PreparedStatement statement = connection.prepareStatement(o.getInsertQuery());
            Field[] fields = o.getClass().getDeclaredFields();
            int i = 0;
            for (Field field : fields) {
                FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
                field.setAccessible(true);
                if (!(annotation.DBSets())) {
                    if (annotation.isCompositeDataType()) {
                        statement.setObject(i + 1, insertAndGetId((Data) field.get(o)));
                    } else if (annotation.isEnum()) {
                        statement.setObject(i + 1, selectId((MusicGenre) field.get(o)));
                    } else {
                        statement.setObject(i + 1, field.get(o));
                    }
                    i++;
                }
            }
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return (Integer) resultSet.getObject("id");
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private int selectId(MusicGenre genre) { //TODO переделать под любой enum
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            PreparedStatement statement = connection.prepareStatement("select id from music_genres where name = ?");
            statement.setString(1, genre.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MusicGenre selectMusicGenre(int id) { //TODO переделать под любой enum
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            PreparedStatement statement = connection.prepareStatement("select name from music_genres where id = " + id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return MusicGenre.valueOf(resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Data getObjectById(DataTypes dataType, int id) {
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from " + dataType.getTableName() + " where id = " + id + ";");
            return getObjectFromResultSet(rs, dataType);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Data getObjectFromResultSet(ResultSet resultSet, DataTypes dataType) throws SQLException, IllegalAccessException {
        Data o = dataType.getNewInstance();
        if (!(resultSet.next())) return null;
        for (Field field : dataType.getDeclaredFields()) {
            field.setAccessible(true);
            FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
            if (annotation.isCompositeDataType()) {
                field.set(o, getObjectById(annotation.compositeDataType(), (Integer) resultSet.getObject(annotation.ColumnName())));
            } else {
                Object value = resultSet.getObject(annotation.ColumnName());
                if (value.getClass().equals(Double.class)) { //TODO жесть
                    Float newValue = resultSet.getFloat(annotation.ColumnName());
                    field.set(o, newValue);
                } else field.set(o, resultSet.getObject(annotation.ColumnName()));
            }
        }
        return o;
    }

    private ArrayList<Data> getObjectsFromResultSet(ResultSet resultSet, DataTypes dataType) throws SQLException, IllegalAccessException {
        ArrayList<Data> list = new ArrayList<>();
        while (resultSet.next()) {
            Data o = dataType.getNewInstance();
            for (Field field : dataType.getDeclaredFields()) {
                field.setAccessible(true);
                FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
                if (annotation.isCompositeDataType()) {
                    Object id = resultSet.getObject(annotation.ColumnName());
                    if (id != null) field.set(o, getObjectById(annotation.compositeDataType(), (Integer) id));
                } else if (annotation.isEnum()) {
                    field.set(o, selectMusicGenre((Integer) resultSet.getObject(annotation.ColumnName())));
                } else
                    field.set(o, resultSet.getObject(annotation.ColumnName()));
            }
            list.add(o);
        }
        return list;
    }
}
