package liguoyu.proxy;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import liguoyu.proxy.annotation.Cached;
import liguoyu.proxy.annotation.Performance;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liguoyu@58.com on 16/4/22.
 */
public abstract class AbstractProxy implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractProxy.class);

    private static ConcurrentHashMap allService = new ConcurrentHashMap();

    protected static <T> T getProxy(Class<T> c, Class<? extends AbstractProxy> p) {
        String key = c.getName();
        if (allService.contains(key)) {
            return (T) allService.get(key);
        } else {
            try {
                AbstractProxy instance = p.newInstance();
                T srv = (T) Enhancer.create(c, instance);
                T temp = (T) allService.putIfAbsent(key, srv);
                return temp == null ? srv : temp;
            } catch (Exception e) {
                logger.error("getProxy error " + c.getName() + ", " + p.getName(), e);
                return null;
            }
        }
    }

    private static ExecutorService threadpool = Executors.newCachedThreadPool();

    private static ConcurrentHashMap<String, Cache<String, Object>> proxyCache
            = new ConcurrentHashMap<String, Cache<String, Object>>();

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        Cached localCached = method.getAnnotation(Cached.class);

        Cache<String, Object> cache = null;
        boolean mayCache = true;
        String cachekey = "";

        if (localCached != null) {

            String key = o.getClass().getSuperclass().getName() + "." + method.getName();
            Class<?>[] parTypes = method.getParameterTypes();
            for (Class<?> cls : parTypes) {
                //非primitive的参数函数,不去做缓存
                if (!cls.isPrimitive() && !cls.getName().equals("java.lang.String")) {
                    mayCache = false;
                    logger.warn("fatal warning!! [params not primitive, method will not be cached. key=" + key +" ]");
                    break;
                }
            }

            if (mayCache) {
                if (proxyCache.containsKey(key)) {
                    cache = proxyCache.get(key);
                } else {
                    int maxsize = localCached.maxsize();
                    long expire = localCached.expire();
                    cache = CacheBuilder.newBuilder().maximumSize(maxsize)
                            .expireAfterAccess(expire, TimeUnit.MILLISECONDS).build();
                    Cache<String, Object> temp = proxyCache.putIfAbsent(key, cache);
                    if (temp != null) {
                        cache = temp;
                    }
                }

                StringBuilder paramkey = new StringBuilder(50);
                for (Object par : objects) {
                    paramkey.append(par).append(",");
                }
                paramkey.append("END");


                cachekey = key + "@" + paramkey.toString();
                Object re = cache.getIfPresent(cachekey);

                //返回cache的结果
                if (re != null) {
                    logger.debug("get from cache cachekey=" + cachekey + ",re=" + LogUtils.simplifyParamsStr(re));
                    return re;
                }
            }
        }

        Object result = null;
        Performance perform = method.getAnnotation(Performance.class);
        if (perform != null) {
            long timeout = perform.timeout();
            int tps = perform.srv_tps();
            String methodName = method.getName();
            boolean callable = bAllowCall(method.getName(), tps);
            if (!callable) {
                logger.error("fatal warning!! [methodName=" + methodName + " calls too frq,tps=" + tps + "]");
                return null;
            }

            ProxyRunner runnner = new ProxyRunner(this, o, method, objects, methodProxy);
            Future<Object> futrue = threadpool.submit(runnner);
            try {
                result = futrue.get(timeout, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                //超时情况不需要缓存
                mayCache = false;
                logger.error(e + " [ method :" + method.getName() + ",timeout error,time=" + timeout + "]");
                throw e;
            }
        } else {
            result = execute(o, method, objects, methodProxy);
        }

        if (mayCache && cachekey != null && cachekey.length() > 0) {
            logger.debug("put cache cachekey=" + cachekey + ",result=" + LogUtils.simplifyParamsStr(result));
            cache.put(cachekey, result);
        }
        return result;
    }

    private ConcurrentHashMap<String, AtomicInteger> time_count = new ConcurrentHashMap<String, AtomicInteger>();
    private ConcurrentLinkedQueue<Object[]> cacheKeyQueue = new ConcurrentLinkedQueue<Object[]>();

    private boolean bAllowCall(String method, int thred) {
        long cursec = System.currentTimeMillis() / 1000;
        String key = method + "@" + cursec;
        AtomicInteger counter = null;
        if (time_count.containsKey(key)) {
            counter = time_count.get(key);
        } else {
            counter = new AtomicInteger(0);
            AtomicInteger tempcounter = time_count.putIfAbsent(key, counter);
            if (tempcounter != null) {
                counter = tempcounter;
                addAndEvictKeys(key, cursec);
            }
        }

        int ct = counter.incrementAndGet();
        if (ct > thred) {
            return false;
        }

        return true;
    }

    private void addAndEvictKeys(String key, long timenow) {
        cacheKeyQueue.add(new Object[]{key, timenow});
        Object[] ele = cacheKeyQueue.poll();
        if (ele != null) {
            String k = (String) ele[0];
            long time = (Long) ele[1];
            if (timenow - 3 < time) {
                cacheKeyQueue.add(ele);
            }else{
                time_count.remove(k);
            }
        }
    }


    protected class ProxyRunner implements Callable {
        AbstractProxy proxy;
        Object o;
        Method method;
        Object[] objects;
        MethodProxy methodProxy;

        public ProxyRunner(AbstractProxy proxy, Object o, Method method, Object[] objects, MethodProxy methodProxy) {
            this.proxy = proxy;
            this.o = o;
            this.method = method;
            this.objects = objects;
            this.methodProxy = methodProxy;

        }

        public Object call() throws Exception {
            try {
                return proxy.execute(o, method, objects, methodProxy);
            } catch (Throwable throwable) {
                throw new Exception(throwable);
            }
        }
    }

    public abstract Object execute(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable;
}
