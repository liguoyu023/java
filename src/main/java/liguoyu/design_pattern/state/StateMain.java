package liguoyu.design_pattern.state;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class StateMain {
    public static void main(String[] args){
        Context context = new Context();
        StateA stateA = new StateA();
        StateB stateB = new StateB();
        context.setState(stateA);
        context.request();
        context.setState(stateB);
        context.request();
    }
}  
