package liguoyu.design_pattern.static_factory;

/**
 * Created by liguoyu@58.com on 2016/12/6.
 */
public class StaticFactoryTest {
    public static void main(String[] args){
        Sender sender = StaticFactory.getMailSender();
        sender.send();
        sender = StaticFactory.getSmsSender();
        sender.send();
    }
}  
