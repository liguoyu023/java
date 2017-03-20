package liguoyu.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private static CountDownLatch count = new CountDownLatch(5);
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
//        testock(true);
		testock(false);
        while (true){
            if (count.getCount() == 0){
                System.out.println((System.currentTimeMillis() - start) + "ms");
                System.exit(1);
            }

        }

    }

    private static void testock(boolean fair) {
        final Lock lock = new ReentrantLock2(fair);

        System.out.println("cur lock version : fair?" + fair);
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Job(lock));
            t.setName("" + i);//设置线程名称
            t.start();
        }


    }

    private static class Job implements Runnable {

        Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {

            for (int i = 0; i < 5; i++){
                lock.lock();
                try {
                    System.out.println("Locked by " + Thread.currentThread().getName() + ", waited  "
                            + ((ReentrantLock2) lock).getQuenedThreads());
                    try {
                        TimeUnit.MICROSECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }

            }

            count.countDown();


        }

    }


    private static class ReentrantLock2 extends ReentrantLock {

        private static final long serialVersionUID = -5229790810454946297L;

        public ReentrantLock2(boolean b) {
            super(b);
        }

        //得到当前等待队列中的线程名称
        public List<String> getQuenedThreads() {
            List<String> threadList = new ArrayList<String>();
            Collection<Thread> colls = super.getQueuedThreads();
            Iterator<Thread> it = colls.iterator();
            while (it.hasNext()){
                threadList.add(it.next().getName());
            }
            return threadList;
        }
    }
}