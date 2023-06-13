package data.description;

import java.lang.reflect.Field;

/**
 * Класс, от которого наследуются все классы, предназначенные для хранения данных.
 * Наследников этого класса можно формировать по значениям полей, введенным пользователем
 */
public abstract class Data {
    public abstract Integer getId();

    public String toString() {
        DataTypes dataType = DataTypes.getTypeByName(this.getClass().getName());
        StringBuilder str = new StringBuilder(dataType.getName() + ": \n");
        for (Field field : this.getClass().getDeclaredFields()) {
            FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
            if (annotation.printable()) {
                str.append("    ").append(annotation.name()).append(": ");
                field.setAccessible(true);
                try {
                    str.append(field.get(this)).append("\n");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return str.toString();
    }

    public String getInsertQuery() {
        DataTypes dataType = DataTypes.getTypeByName(this.getClass().getName());
        StringBuilder query = new StringBuilder("insert into " + dataType.getTableName());
        query.append(" (");
        int k = 0;
        for (Field field : dataType.getDeclaredFields()) {
            FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
            if (!(annotation.DBSets())) {
                query.append(field.getAnnotation(FieldAnnotation.class).ColumnName());
                query.append(", ");
                k++;
            }
        }
        query = new StringBuilder(query.substring(0, query.length() - 2) + ") values ");
        query.append("(");
        query.append("?, ".repeat(k));
        query = new StringBuilder(query.substring(0, query.length() - 2) + ") returning id;");
        return query.toString();
    }

    public String getUpdateQuery(int id) { //TODO объединить с предыдущим методом
        DataTypes dataType = DataTypes.getTypeByName(this.getClass().getName());
        StringBuilder query = new StringBuilder("update " + dataType.getTableName() + " set");
        query.append(" (");
        int k = 0;
        for (Field field : dataType.getDeclaredFields()) {
            FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
            if (!(annotation.DBSets() || annotation.isCompositeDataType())) {
                query.append(field.getAnnotation(FieldAnnotation.class).ColumnName());
                query.append(", ");
                k++;
            }
        }
        query = new StringBuilder(query.substring(0, query.length() - 2) + ") = ");
        query.append("(");
        query.append("?, ".repeat(k));
        query = new StringBuilder(query.substring(0, query.length() - 2) + ") ");
        query.append("where id = ").append(id).append(";");
        return query.toString();
    }
}