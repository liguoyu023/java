package liguoyu.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by liguoyu@58.com on 2016/9/2.
 */
public class CyclicBarrierTest {
    private static final int THREAD_NUM = 5;
        /**
         * @param args
         */
        public static void main(String[] args) {
            // TODO Auto-generated method stub
            CyclicBarrier cb = new CyclicBarrier(THREAD_NUM, new Runnable() {
                //当所有线程到达barrier时执行
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    System.out.println("Inside Barrier");

                }
            });

            for(int i=0;i<THREAD_NUM;i++){
                new Thread(new WorkerThread(cb)).start();
            }
        }


    public static class WorkerThread implements Runnable{

        CyclicBarrier barrier;

        public WorkerThread(CyclicBarrier b){
            this.barrier = b;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
                System.out.println("Worker's waiting");
                //线程在这里等待，直到所有线程都到达barrier。
                barrier.await();
                System.out.println("ID:"+Thread.currentThread().getId()+" Working");
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}  
