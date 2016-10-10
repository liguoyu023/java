package liguoyu.design_pattern.chain_of_responsibility;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 * 抽象处理者
 */
public abstract class Handler {

    /**
     * 持有后继的责任对象
     */
    protected Handler successor;

    /**
     * 处理请求的方法
     */
    public abstract void handleRequest();

    /**
     * 取值方法
     * @return
     */
    public Handler getSuccessor(){
        return successor;
    }

    /**
     * 赋值方法，设置后继的责任对象
     */
    public void setSuccessor(Handler successor){
        this.successor = successor;
    }
}  
