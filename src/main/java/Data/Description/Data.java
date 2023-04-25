package Data.Description;

import java.lang.reflect.Field;

/**
 * Класс, от которого наследуются все классы, предназначенные для хранения данных.
 * Наследников этого класса можно формировать по значениям полей, введенным пользователем
 */
public class Data {
    public String toString(String className) {
        String str = className + ": \n";
        for (Field field : this.getClass().getDeclaredFields()) {
            str += "    " + field.getAnnotation(FieldAnnotation.class).name() + ": ";
            field.setAccessible(true);
            try {
                str += field.get(this) + "\n";
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return str;
    }
}