package data.description;

import data.generation.Generator;

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
     * @return поле должно генерироваться автоматически?
     */
    boolean isGenerate() default false;

    /**
     * @return класс, выполняющий генерацию поля
     */
    Class<? extends Generator> generator() default Generator.class;

    /**
     * @return поле - объект составного типа? (Coordinates, Album)
     */
    boolean isCompositeDataType() default false;

    /**
     * @return поле - объект перечисляемого типа?
     */
    boolean isEnum() default false;

}
