package data.description;

import java.lang.reflect.Field;

/**
 * Класс, от которого наследуются все классы, предназначенные для хранения данных.
 * Наследников этого класса можно формировать по значениям полей, введенным пользователем
 */
public class Data {
    public String toString(String className) {
        StringBuilder str = new StringBuilder(className + ": \n");
        for (Field field : this.getClass().getDeclaredFields()) {
            str.append("    ").append(field.getAnnotation(FieldAnnotation.class).name()).append(": ");
            field.setAccessible(true);
            try {
                str.append(field.get(this)).append("\n");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return str.toString();
    }
}