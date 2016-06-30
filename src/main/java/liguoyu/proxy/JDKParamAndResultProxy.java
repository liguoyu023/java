package liguoyu.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by liguoyu@58.com on 2016/6/30.
 */
public class JDKParamAndResultProxy implements InvocationHandler{

    private Object object;

    public JDKParamAndResultProxy(Object object) {
        this.object = object;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
