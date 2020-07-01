package com.camel.survey.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Log {
    String moduleName() default "";
    String option() default "";
}
