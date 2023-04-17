package Data.Description;

import Data.Generation.Generator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldAnnotation {
    String name();

    boolean nullable() default true;

    boolean isValidate() default false;

    int moreThen() default 0;

    boolean isGenerate() default false;

    Class<? extends Generator> generator() default Generator.class;


}
