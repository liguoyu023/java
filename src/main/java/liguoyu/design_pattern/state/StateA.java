package liguoyu.design_pattern.state;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class StateA implements State {

    @Override
    public void handle() {
        System.out.println("A");
    }
}
