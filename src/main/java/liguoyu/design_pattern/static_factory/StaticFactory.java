package liguoyu.design_pattern.static_factory;

/**
 * Created by liguoyu@58.com on 2016/12/6.
 */
public class StaticFactory {

    public static Sender getMailSender(){
        return new MailSender();
    }

    public static Sender getSmsSender(){
        return new SmsSender();
    }
}  
