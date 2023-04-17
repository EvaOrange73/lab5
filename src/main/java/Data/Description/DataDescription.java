package Data.Description;

import java.lang.reflect.Field;

public class DataDescription {
    public String toString(String className) {
        String str = className + ": \n";
        for (Field field : this.getClass().getDeclaredFields()) {
            str += "    " + field.getAnnotation(FieldAnnotation.class).name() + ": ";
            field.setAccessible(true);
            try {
                str += field.get(this) + "\n";
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e); //TODO когда возникает?
            }
        }
        return str;
    }
}