package data.description;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Аннотация для полей подклассов Data
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldAnnotation {
    /**
     * @return Название поля на русском языке (используется для сообщений пользователю)
     */
    String name();

    /**
     * @return Название колонки в базе данных
     */
    String ColumnName();

    /**
     * @return Нужно ли печатать поле пользователю
     */
    boolean printable() default true;

    /**
     * @return Может ли поле быть null
     */
    boolean nullable() default true;

    /**
     * @return есть ли на поле дополнительные ограничения типа "значение должно быть больше moreThen"
     */
    boolean isValidate() default false;

    /**
     * @return число, больше которого должно быть значение поля
     */
    int moreThen() default 0;

    /**
     * @return поле устанавливается базой данных
     */
    boolean DBSets() default false;

    /**
     * Поле устанавливается сервером
     */
    boolean serverSets() default false;

    /**
     * @return поле - объект составного типа? (Coordinates, Album)
     */
    boolean isCompositeDataType() default false;

    /**
     * @return тип поля, если оно составное
     */
    DataTypes compositeDataType() default DataTypes.DEFAULT;

    /**
     * @return поле - объект перечисляемого типа?
     */
    boolean isEnum() default false;

}
