package com.java.core.annotation;

import java.lang.annotation.*;

/**
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/25 20:28
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Property {
    String editor() default "";
}
