package liguoyu.ioc;

import java.lang.annotation.*;

/**
 * Created by liguoyu@58.com on 2016/6/28.
 */
@Target({ElementType.TYPE,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    String name() default "";
}
