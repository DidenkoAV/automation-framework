package framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestParams {
    String browser() default "chrome";
    String csvPath() default "undefined";
    int scenario() default 0;
    int runId() default 0;
}
