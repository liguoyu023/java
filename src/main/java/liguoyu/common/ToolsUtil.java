package liguoyu.common;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

/**
 * 
 * 创建日期:2013-4-17
 * Title:文件所属模块（必须填写）
 * Description：对本文件的详细描述，原则上不能少于30字
 * @author Administrator
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：认为有必要的其他信息
 */
public class ToolsUtil {
	
	/**
	 * 获取客户端IP
	 * @return
	 * @throws Exception
	 */
	public static String getIpFromClient(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-real-ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取真实IP
	 * @param request
	 * @return
	 */
	public static String getRealIp(HttpServletRequest request) {
		String ip = getHeader(request, "x-forwarded-for");
		if (StringUtils.isNotBlank(ip))
			return pickIp(ip);
		ip = getHeader(request, "Proxy-Client-IP");
		if (StringUtils.isNotBlank(ip))
			return pickIp(ip);
		ip = getHeader(request, "WL-Proxy-Client-IP");
		if (StringUtils.isNotBlank(ip))
			return pickIp(ip);
		else
			return request.getRemoteAddr();
	}
	
	private static String pickIp(String ip){
		if(ip.indexOf(",")>-1)
			return ip.split(",")[0];
		return ip;
	}
	
	private static String getHeader(HttpServletRequest request, String headName) {
		String value = request.getHeader(headName);
		return !StringUtils.isBlank(value) && !"unknown".equalsIgnoreCase(value) ? value : "";
	}
	
	
	
	
	
	/**
	 * 获取int值，异常返回默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Object obj,int defaultValue){
		try{
			if(obj==null || StringUtil.isEmpty(obj.toString().trim()))
				return defaultValue;
			return Integer.valueOf(obj.toString());
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 获取long值，异常返回默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(Object obj,long defaultValue){
		try{
			if(obj==null || StringUtil.isEmpty(obj.toString().trim()))
				return defaultValue;
			return Long.valueOf(obj.toString());
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 获取long[]数组
	 * @param strArray
	 * @return
	 */
	public static Long[] getLongArray(String[] strArray){
		if(strArray==null) return null;
		Long [] longArray=new Long[strArray.length];
		for(int i=0;i<strArray.length;i++){
			longArray[i]=getLong(strArray[i], 0);
		}
		return longArray;
	}
	
	/**
	 * 获取double值,异常返回默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static double getDouble(Object obj,double defaultValue){
		try {
			if(obj==null||StringUtil.isEmpty(obj.toString().trim()))
				return defaultValue;
			return Double.valueOf(obj.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 获取String值，为空返回默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Object obj,String defaultValue){
		if(obj==null || StringUtil.isEmpty(obj.toString().trim()))
			return defaultValue;
		return obj.toString().trim();
	}
	
	/**
	 * 获取Boolean值，为空返回默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Object obj,boolean defaultValue){
		if(obj==null || StringUtil.isEmpty(obj.toString().trim()))
			return defaultValue;
		return Boolean.valueOf(obj.toString());
	}
	
	/**
	 * 保留小数点后两位，适用于余额显示
	 * @param number
	 * @return
	 */
	public static String getMoneyFormatString(double number){
		if(number == 0)
			return "0.0";
		return new DecimalFormat("#0.00").format(number); 
	}



	/**
	 * 截取字符串长度
	 * @param str
	 * @param i
	 * @return
	 */
	public static String cutString(String str,int i){
		if(str.length()>i){
			return str.substring(0,i)+"...";
		}else{
			return str;
		}
	}
	

	/**
	 * 过滤掉QQ表情之类的数据
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException{
		if(text!=null && text.length()>0) {
			byte[] bytes = text.getBytes( "utf-8" );
			ByteBuffer buffer = ByteBuffer.allocate( bytes.length );
			int i = 0;
			while( i < bytes.length ){
				short b = bytes[ i ];
				if( b > 0 ){
					buffer.put( bytes[ i++ ] );
					continue;
				}
				b += 256;
				if( ( b ^ 0xC0 ) >> 4 == 0 ){
					buffer.put( bytes, i, 2 );
					i += 2;
				}
				else if( ( b ^ 0xE0 ) >> 4 == 0 ){
					buffer.put( bytes, i, 3 );
					i += 3;
				}
				else if( ( b ^ 0xF0 ) >> 4 == 0 ){
					i += 4;
				}
				else{
					buffer.put( bytes[ i++ ] );
				}
			}
			buffer.flip();
			String  str = new String( buffer.array(), "utf-8" );
			// 过滤出汉字、数字、字母、下划线的内容
	        str = str.replaceAll("^[\\u4e00-\\u9fa5|A-Za-z0-9\\-（）,，.& \\(\\)]$", "");
			return str;
		}else {
			return "";
		}
	}
	
	public static String  addparam(String url,String param){
		if(url==null){
			return "";
		}
		if (url.indexOf("?") > -1) {
			url += "&" + param;
		} else {
			url += "?" + param;
		}
		return url;
	}
}
