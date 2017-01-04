package liguoyu.arithmetic;

/**
 * Created by liguoyu@58.com on 2017/1/3.
 */
public class Arithmethtic {

    public static void main(String[] args){
        g1();
    }

    /** 
        1、经典数学问题：百鸡问题的变形

        题目描述：有36个人，36块砖，每人搬了一次，正好搬完。其中男每人每次搬4块，
        女每人每次搬3块，小孩两人每次搬一块。问男、女、小孩各多少人？
    */
    public static void g1(){

        int people = 36;
        int zhuan = 36;
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <=12; j++) {
                if(i*4+j*3+(36-i-j)/2==36&&(36-i-j)%2==0){
                    System.out.println("男:"+i);
                    System.out.println("女:"+j);
                    System.out.println("小孩:"+(36-i-j));
                }
            }
        }
    }



}  
