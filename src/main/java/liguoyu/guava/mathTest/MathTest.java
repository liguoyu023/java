package liguoyu.guava.mathTest;

import com.google.common.math.DoubleMath;
import org.junit.Assert;

import java.math.RoundingMode;

/**
 * Created by liguoyu@58.com on 2016/7/22.
 */
public class MathTest {

    public static void main(String[] args){
        //模糊比较 如果第一个参数跟第二个参数的差小于等于第三个参数则返回true
        Assert.assertTrue(DoubleMath.fuzzyEquals(1d, 5d, 4d));
        //阶乘
        Assert.assertTrue(DoubleMath.factorial(5)==5*4*3*2*1);
        //验证是否是绝对的整数
        Assert.assertTrue(DoubleMath.isMathematicalInteger(41.00));
        //判断是否是2的倍数
        Assert.assertTrue(DoubleMath.isPowerOfTwo(1024));
        //将double类型的值近似成int类型的值 通过第二个参数指定四舍五入的策略
        Assert.assertTrue(DoubleMath.roundToInt(12.88, RoundingMode.HALF_UP)==13);
    }
}  
