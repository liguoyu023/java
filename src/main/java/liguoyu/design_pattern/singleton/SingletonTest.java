package liguoyu.design_pattern.singleton;

/**
 * Created by liguoyu@58.com on 2017/1/3.
 */
public class SingletonTest {
    /***
     * 方式1:静态的私有的成员变量+私有的构造函数+公共的静态的获取方法
     * 缺点 多线程调用getinstance方法的时候会导致instance多次创建
     */
    private static SingletonTest instance;
    private SingletonTest(){

    }

    public static SingletonTest getInstance(){
        if(instance == null){
            instance = new SingletonTest();
        }
        return instance;
    }

    /***
     * 优化方式1：在方法上添加synchronized关键字
     */
    public static synchronized SingletonTest getInstance2(){
        //sysnchronized关键字位于方法上的时候是锁住整个对象 这样的话在性能上有所损失
        if(instance == null){
            instance = new SingletonTest();
        }
        return instance;
    }

    /***
     * 优化方式2：在方法体内部添加同步判断
     */
    public static SingletonTest getInstance3(){
        if(instance == null){
            synchronized (instance){
                if(instance == null){
                    //在Java指令中创建对象和赋值操作是分开进行的
                    //无法保证时序性
                    //有可能instance的指针已经不为空了 但是没有赋值
                    //这样的话其他线程有可能会有空指针的问题
                    instance = new SingletonTest();
                }
            }
        }
        return instance;
    }

    /***
     * 优化方式3：使用内部类进行维护
     */
    public static class SingletonFactory{
        private static SingletonTest instance = new SingletonTest();
    }

    public static SingletonTest getInstance4(){
        return SingletonTest.instance;
    }

}  
