package liguoyu.common;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, //最小线程数
            100, //最多100个线程
            10,//线程空闲时间为10秒，超过10秒后空闲线程才会被回收
            TimeUnit.SECONDS, //时间单位
            new LinkedBlockingQueue<Runnable>()//单个线程执行队列长度
    );
    private ThreadPool(){}

    public static ThreadPoolExecutor getThreadPool(){
        return executor;
    }

}
