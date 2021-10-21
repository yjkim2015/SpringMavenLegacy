package com.legacy.commerce.logging;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OrderLogging {
    public static final int SYSTART = 1;
    public static final int RESULT = 2;
    public static final int EXCEPTION = 3;

    int[] excludes() default 0;
}
