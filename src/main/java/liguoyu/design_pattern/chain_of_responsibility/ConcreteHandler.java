package liguoyu.design_pattern.chain_of_responsibility;

/**
 * 具体处理者角色
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class ConcreteHandler extends Handler {

    @Override
    public void handleRequest() {
        /**
         * 判断是否有后继的责任对象
         * 如果有的话转发给后继的责任对象
         * 如果没有的话，处理请求
         */
        if(getSuccessor() != null){
            System.out.println("放过请求");
            getSuccessor().handleRequest();
        }else {
            System.out.println("处理请求");
        }
    }
}
