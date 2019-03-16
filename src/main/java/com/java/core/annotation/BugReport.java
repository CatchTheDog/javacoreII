package com.java.core.annotation;

public @interface BugReport {
    boolean showStopper() default false;

    String reportBy() default "";
}
