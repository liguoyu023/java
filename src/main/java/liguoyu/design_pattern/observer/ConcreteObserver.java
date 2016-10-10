package liguoyu.design_pattern.observer;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class ConcreteObserver extends Observer {

    private String observerState;
    private String name;
    private ConcreteSubject subject;

    public ConcreteObserver(String name, ConcreteSubject subject) {
        this.name = name;
        this.subject = subject;
    }

    @Override
    public void update() {
        observerState = subject.getSubjectState();
        System.out.println("The observer's state of "+name+" is"+observerState);
    }
}
