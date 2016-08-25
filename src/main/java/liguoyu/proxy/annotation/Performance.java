package liguoyu.proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 吞度量配置
 * Created by liguoyu@58.com on 16/4/23.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Performance {
    //tps控制
    int srv_tps() default 500;
    //超时时间 毫秒
    long timeout() default 2000;
}
