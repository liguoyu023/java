package liguoyu.design_pattern.chain_of_responsibility;

/**
 * 枚举类型的责任链实现
 * Created by liguoyu@58.com on 2016/10/10.
 */
public enum EnumImpls {

    A{
        @Override
        public void dealReqest() {
            if(handler != null){
                System.out.println("放过请求");
                handler.dealReqest();
            }
                super.dealReqest();
        }
    },B{
        @Override
        public void dealReqest() {
            if(handler != null){
                System.out.println("放过请求");
                handler.dealReqest();
            }
            super.dealReqest();
        }
    };

    EnumImpls handler;

    public void dealReqest(){
        System.out.println("处理");
    }

    public void setHandler(EnumImpls handler){
        this.handler = handler;
    }

    public EnumImpls getHandler(){
        return handler;
    }



}
