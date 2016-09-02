package liguoyu.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * Exchanger 用于两个线程之间进行数据交换
 * 当一个线程执行了exchange方法后 切换到另外一个线程 当前线程处于阻塞状态
 * 而另外一个线程执行了exchange方法后 可以将其获得的结果传递给之前的线程 之前的线程拿到返回结果后再做其他处理
 * Exchanger 只能用于两个线程之间
 * 原理：
 *  当第一个线程执行的时候
 *  将第一个线程执行exchange方法的结果交换到另外一个Node中
 *  然后中断第一个线程
 *  第二个线程再从spot数组中拿到自己的线程id hash到的下标下的值
 *  然后再进行下一步代码
 *  整个过程基于CAS
 * Created by liguoyu@58.com on 2016/9/2.
 */
public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<List<Integer>> exchanger = new Exchanger<List<Integer>>();
        new Consumer(exchanger).start();
        new ListFill(exchanger).start();
    }


    static class ListFill extends Thread {
        List<Integer> list = new ArrayList<Integer>();
        Exchanger<List<Integer>> exchanger = null;

        public ListFill(Exchanger<List<Integer>> exchanger) {
            super();
            this.exchanger = exchanger;
        }


        @Override
        public void run() {
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                list.clear();
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                try {
                    list = exchanger.exchange(list);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        List<Integer> list = new ArrayList<Integer>();
        Exchanger<List<Integer>> exchanger = null;

        public Consumer(Exchanger<List<Integer>> exchanger) {
            super();
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    list = exchanger.exchange(list);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.print(list.get(0) + ", ");
                System.out.print(list.get(1) + ", ");
                System.out.print(list.get(2) + ", ");
                System.out.print(list.get(3) + ", ");
                System.out.println(list.get(4) + ", ");
            }
        }
    }

}
