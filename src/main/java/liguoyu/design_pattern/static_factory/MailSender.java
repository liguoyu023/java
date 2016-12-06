package liguoyu.design_pattern.static_factory;

/**
 * Created by liguoyu@58.com on 2016/12/6.
 */
public class MailSender implements Sender{

    @Override
    public void send() {
        System.out.println("send mail");
    }
}
