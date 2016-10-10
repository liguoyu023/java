package liguoyu.design_pattern.observer;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class ProGramMain {
    public static void main(String[] args){
        ConcreteSubject subject = new ConcreteSubject();

        subject.attach(new ConcreteObserver("A",subject));
        subject.attach(new ConcreteObserver("B",subject));
        subject.attach(new ConcreteObserver("C",subject));

        subject.setSubjectState("ready");
        subject.notifyOb();
    }
}  
