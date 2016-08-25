package liguoyu.proxy;


import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * bll代理类
 * Created by liguoyu@58.com on 16/4/22.
 */
public class BllProxy extends AbstractProxy {

    private static final Logger logger = LoggerFactory.getLogger(BllProxy.class);

    public static <T> T getProxy(Class<T> bllclass) {
        return AbstractProxy.getProxy(bllclass, BllProxy.class);
    }

    public Object execute(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        long begin = System.currentTimeMillis();
        String key = obj.getClass().getSuperclass().getName() + "." + method.getName();
        Object result = methodProxy.invokeSuper(obj, objects);

        long costTime = System.currentTimeMillis() - begin;
        String logStr = key + " time: " + costTime;

        //bll调用日志
        String log = ("BllProxy log " + logStr + " param : " + LogUtils.simplifyParamsStr(objects)
                + " result:" + LogUtils.simplifyParamsStr(new Object[]{result}));

        return result;
    }

}
