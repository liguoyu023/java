package liguoyu.proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存配置
 * Created by liguoyu@58.com on 16/4/22.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {
    //缓存最大量
    int maxsize() default 1000;
    //缓存过期时间 毫秒
    long expire() default 60;
    //缓存标示,如果不设置,则为"类名+方法名",根据业务情况设定标示,可以对缓存进行复用,可以节省存储空间.
//    String cachename() default "";
    //存储类型 1=本地缓存 2=redis缓存
//    String[] cha();
}
