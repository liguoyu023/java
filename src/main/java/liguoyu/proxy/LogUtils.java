package liguoyu.proxy;

/**
 * Created by liguoyu@58.com on 16/8/11.
 */
public class LogUtils {

    public static  String simplifyParamsStr(Object objParam) {
        return simplifyParamsStr(new Object[]{objParam});
    }

    public static  String simplifyParamsStr(Object[] objParams) {
        String result = "[";
        if (objParams != null) {
            for (int i = 0; i < objParams.length; i++) {
                Object obj = objParams[i];
                if (i == 0) {
                    result += ObjectToString(obj);
                } else {
                    result += "," + ObjectToString(obj);
                }
            }
        }
        result += "]";
        return result;
    }

    private static String ObjectToString(Object obj) {
        if (obj == null) {
            return "";
        }
        String str = obj.toString();
        String s = "";
        if (str.length() > 200) {
            s = obj.getClass().getName();
            s += "...len=";
            s += str.length();
            s += ",sum=" + str.substring(0,100);
        } else {
            s = str;
        }
        return s;
    }
}
