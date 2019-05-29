package com.sayan.easylistwidget.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * maps a method (must be a getter method) to a view ID in a recycler child layout
 * @author  Sayan Mukherjee
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ID {
    /**
     * return the resource ID of the view
     * @return the resource ID of the view
     */
    int value() default 0;
}
