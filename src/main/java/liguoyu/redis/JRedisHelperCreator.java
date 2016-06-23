package liguoyu.redis;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JredisHelper创建器，每次返回一个连接池 
 * @date   2016年1月15日
 */
public class JRedisHelperCreator {
	
	private static final int DEFAULT_MIN_SIZE = 2;
	
	private static final int DEFAULT_MAX_SIZE = 30;
	
	private static final int DEFAULT_MAX_IDEL = 2000;
	
	private static final int DEFAULT_CONN_TIME_OUT = 500;
	
	private static final int DEFAULT_READ_TIME_OUT = 300;
	
	
	public  static JRedisClient creatInstance(String fileName) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		File file = new File(fileName);
		if(null != file && file.isFile()){
				DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
				domFactory.setNamespaceAware(true); // never forget this!
				DocumentBuilder builder = domFactory.newDocumentBuilder();
				Document document = builder.parse(file);
				XPathFactory factory = XPathFactory.newInstance();
				XPath xpath = factory.newXPath();
				NodeList nodes = (NodeList) xpath.evaluate(	"//configuration/redis", document, XPathConstants.NODESET);
				String keyPrefixString = null;
				List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>();
				int connTimeOut = DEFAULT_CONN_TIME_OUT;
				int maxSize = DEFAULT_MAX_SIZE;
				int intMaxIdel = DEFAULT_MAX_IDEL;
				int readTimeOut = DEFAULT_READ_TIME_OUT;
				int minIdel = DEFAULT_MIN_SIZE;
				boolean checkAlive = false;
				
				
				if(nodes != null && nodes.getLength() > 0){
					Node keyPrefix = (Node) xpath.evaluate("@keyPrefix", nodes.item(0), XPathConstants.NODE);
					keyPrefixString = NodeUtil.parseNode2String(keyPrefix, null);
				}
				
				NodeList poolServer = (NodeList) xpath.evaluate("//configuration/redis/pool", document, XPathConstants.NODESET);
				if(null != poolServer && poolServer.getLength() > 0){
					Node timeOut = (Node) xpath.evaluate("@connTimeOut", poolServer.item(0), XPathConstants.NODE);
					connTimeOut = NodeUtil.parseNode2Integer(timeOut, DEFAULT_CONN_TIME_OUT);
					Node readTimeOutNode = (Node) xpath.evaluate("@readTimeOut", poolServer.item(0), XPathConstants.NODE);
					readTimeOut = NodeUtil.parseNode2Integer(readTimeOutNode, DEFAULT_READ_TIME_OUT);
					Node checkAlie = (Node) xpath.evaluate("@aliveCheck", poolServer.item(0), XPathConstants.NODE);
					checkAlive = NodeUtil.pareNode2Boolean(checkAlie,false);
					Node minNode = (Node) xpath.evaluate("@minConn", poolServer.item(0), XPathConstants.NODE);
					NodeUtil.parseNode2Integer(minNode, DEFAULT_MIN_SIZE);
				}
				
				NodeList nodesServer = (NodeList) xpath.evaluate("//configuration/redis/servers/add", document, XPathConstants.NODESET);
				if(null != nodesServer && nodesServer.getLength() > 0){
					for (int j = 0; j < nodesServer.getLength(); j++) {
						Node subnode = (Node) xpath.evaluate("@address", nodesServer.item(j), XPathConstants.NODE);
						String host = NodeUtil.parseNode2String(subnode, null);
						subnode = (Node) xpath.evaluate("@port", nodesServer.item(j), XPathConstants.NODE);
						int port = NodeUtil.parseNode2Integer(subnode, 0);
						subnode = (Node) xpath.evaluate("@password", nodesServer.item(j), XPathConstants.NODE);
						String password = NodeUtil.parseNode2String(subnode, null);
						JedisShardInfo shardInfo = new JedisShardInfo(host, port);
						shardInfo.setTimeout(connTimeOut);
						if(null != password && password.length() > 0){
							shardInfo.setPassword(password);
						}
						jdsInfoList.add(shardInfo);
					}
				}
				
				JedisPoolConfig config =new JedisPoolConfig();
				config.setMinIdle(minIdel);
				config.setMaxIdle(intMaxIdel);
				config.setMaxTotal(maxSize);
				config.setMaxWaitMillis(readTimeOut);
				config.setMaxIdle(intMaxIdel);
				config.setTestWhileIdle(checkAlive);
				config.setTestOnBorrow(true);//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
				ShardedJedisPool sharedPool = new ShardedJedisPool(config,jdsInfoList);
				JRedisClient helper = new JRedisClient(sharedPool,keyPrefixString);
				return helper;
		}
		return null;
	}
}
