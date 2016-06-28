package liguoyu.ioc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * this class use the builder mod to controller the javabean properties
 * Created by liguoyu@58.com on 2016/6/28.
 */
public class JavaClassBuilder {

    private Class clazz;

    public JavaClassBuilder(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * the build function
     *
     * @param target
     * @return
     */
    public JavaClassBuilder builder(Class target) {
        clazz = target;
        return this;
    }


    public Annotation[] getAllAnnotations() {
        Annotation[] annotations;
        annotations = this.clazz.getAnnotations();
        return annotations;

    }

    public Field[] getAllField() {
        Field[] fields;
        fields = this.clazz.getFields();
        return fields;
    }

    public Method[] getMethods() {
        Method[] methods;
        methods = this.clazz.getMethods();
        return methods;
    }


    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
