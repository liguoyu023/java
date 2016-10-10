package liguoyu.design_pattern.observer;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class ConcreteSubject extends Subject {

    private String subjectState;

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
    }
}
