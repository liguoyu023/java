package liguoyu.design_pattern.chain_of_responsibility;

/**
 * Created by liguoyu@58.com on 2016/10/10.
 */
public class EnumImplsMain {
    public static void main(String[] args){
        EnumImpls.A.setHandler(EnumImpls.B);
        EnumImpls.A.dealReqest();
    }
}  
