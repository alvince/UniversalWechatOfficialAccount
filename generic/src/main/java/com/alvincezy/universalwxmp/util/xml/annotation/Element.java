package com.alvincezy.universalwxmp.util.xml.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2016/1/13.
 *
 * @author alvince.zy@gmail.com
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Element {

    /**
     * Annotate tag name. Default ""
     */
    String name() default "";

    boolean raw() default true;

    boolean embed() default false;

    boolean ignore() default false;
}
