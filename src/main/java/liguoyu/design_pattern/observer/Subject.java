package liguoyu.design_pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象主题类
 * Created by liguoyu@58.com on 2016/10/10.
 */
public abstract class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void detach(Observer observer){
        observers.remove(observer);
    }

    public void notifyOb(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

}  
