package liguoyu.design_pattern.chain_of_responsibility;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class Client {

    public static void main(String[] args){
        Handler handler1 = new ConcreteHandler();
        Handler handler2 = new ConcreteHandler();
        handler1.setSuccessor(handler2);

        handler1.handleRequest();
    }

}  
