package liguoyu.redis;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;

/**
 * 结点帮助类。将节点转变为默认值
 * 
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年2月24日
 */
public class NodeUtil {
	public static String parseNode2String(Node node,String defaultVal){
		if(null != node){
			String nodeString = node.getNodeValue();
			if(nodeString != null){
				return nodeString;
			}
		}
		return defaultVal;
	}
	
	public static Integer parseNode2Integer(Node node,Integer defaultVal){
		if(null != node){
			String nodeString = node.getNodeValue();
			if(nodeString != null && StringUtils.isNumeric(nodeString)){
				return Integer.parseInt(nodeString);
			}
		}
		return defaultVal;
	}
	
	public static double parseNode2Double(Node node,double defaultVal){
		if(null != node){
			String nodeString = node.getNodeValue();
			if(nodeString != null){
				return Double.parseDouble(nodeString);
			}
		}
		return defaultVal;
	}
	
	public static float parseNode2Float(Node node,float defaultVal){
		if(null != node){
			String nodeString = node.getNodeValue();
			if(nodeString != null){
				return Float.parseFloat(nodeString);
			}
		}
		return defaultVal;
	}
	
	public static Boolean pareNode2Boolean(Node node,Boolean defaultVal){
		if(null != node){
			String nodeString = node.getNodeValue();
			if(nodeString != null){
				return Boolean.parseBoolean(nodeString);
			}
		}
		return defaultVal;
	}
}
