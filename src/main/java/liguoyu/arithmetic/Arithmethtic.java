package liguoyu.arithmetic;

/**
 * Created by liguoyu@58.com on 2017/1/3.
 */
public class Arithmethtic {


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

    public static void main(String[] args) {
        int n = 5;
        int a[][] = luoxuan(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(" " + a[i][j] + " ");
            }
            System.out.println("\n");
        }

    }

    /**
     * 二维数组螺旋展示
     * @param n 任意数，负数默认为1.
     * @return
     */
    private  static int[][] luoxuan(int n) {
        if (n <= 0) {
            n = 1;
        }
        int a[][] = new int[n][n];
        int top = 0, bottom = n - 1;//上下
        int left = 0, right = n - 1;//左右
        int i = top, j = left;
        int sum = 1;
        while (true) {
            //上
            while (j != right + 1) {
                a[i][j] = sum;
                sum++;
                j++;
            }
            j--;
            if ((sum - 1) == (n * n)) {
                break;//退出条件
            }
            top++;
            i = top;

            //右
            while (i != bottom + 1) {
                a[i][j] = sum;
                sum++;
                i++;
            }
            i--;

            if ((sum - 1) == (n * n)) {
                break;
            }
            right--;
            j = right;
            //下
            while (j != left - 1) {
                a[i][j] = sum;
                sum++;
                j--;
            }
            j++;
            if ((sum - 1) == (n * n)) {
                break;
            }
            bottom--;
            i = bottom;
            //左
            while (i != top - 1) {
                a[i][j] = sum;
                sum++;
                i--;
            }
            i++;
            if ((sum - 1) == (n * n)) {
                break;
            }
            left++;
            j = left;
        }
        System.out.println("n="+n+",i=" + i + ",j=" + j + ",sum=" + sum);
        return a;
    }




}  
