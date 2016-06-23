/**
 * 
 */
package liguoyu.common;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 将异常转化成字符信息
 *
 * <p>detailed comment
 * @author yxd_yxd2008@163.com 2012-3-19
 * @see
 * @since 1.0
 */
public class E2s {
	public static String exception2String(Exception ex){
		String exceptionMessage = "";
		if (ex != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			try {
				ex.printStackTrace(pw);
				exceptionMessage = sw.toString();
			} finally {
				try {
					sw.close();
					pw.close();	
				} catch (Exception e) {
				}
			}
		}
		return exceptionMessage;
	}
}
