package liguoyu.redis;

import org.apache.log4j.Logger;

/**
 * @date   2016年1月15日
 */
public class JRedisProvider {
	
	private static  JRedisProvider instance = null;
	private static final Logger logger = Logger.getLogger(JRedisProvider.class);
	public JRedisClient redis = null;
	
	public JRedisProvider() {
		try {
//			String configPath = WF.getConfigFolder()+ WF.getNamespace() + File.separator + "redis.xml";
			String configPath = "redis.xml";
			logger.info("redis configPath = " + configPath);
			redis = JRedisHelperCreator.creatInstance(configPath);
		} catch (Exception e) {
			logger.error("redis init exception");
		} 
	}
	
	
	public static JRedisProvider getInstance(){
	    if(instance == null ){
	        synchronized(JRedisProvider.class){
	            if(null == instance){
	                instance=new JRedisProvider();
	            }
	        }
	    }
		return instance;
	}
	
}
