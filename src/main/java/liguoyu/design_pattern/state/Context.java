package liguoyu.design_pattern.state;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class Context{

    private State state;

    public void request(){
        System.out.println("被修改啦");
        state.handle();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
