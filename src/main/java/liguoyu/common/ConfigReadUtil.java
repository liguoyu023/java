package liguoyu.common;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * @author Jin
 * @version 0.0.1
 * @brief  read configure which format is key:value
 * @configurefile
 *
 */
public class ConfigReadUtil {
	static final Logger logger = Logger.getLogger(ConfigReadUtil.class);
	private Properties pro = null;

	public ConfigReadUtil(String path) {
		try {
			this.pro = loadProperty(path);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(E2s.exception2String(e));
		}
		
	}

	public ConfigReadUtil(InputStream inputStream) {
		this.pro = new Properties();
		try {
			this.pro.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getString(String key){
		String ret = null;
		try {
			ret =new String(this.pro.getProperty(key).getBytes("ISO-8859-1"),"UTF-8");  //��������
		} catch (Exception e) {
			logger.error(E2s.exception2String(e));
			return null;
		}
		return ret;
	}

	public int getInt(String key) throws Exception {
		try {
			return Integer.parseInt(this.pro.getProperty(key));
		} catch (Exception e) {
			throw new Exception("key:" + key);
		}
	}

	public double getDouble(String key) throws Exception {
		try {
			return Double.parseDouble(this.pro.getProperty(key));
		} catch (Exception e) {
			throw new Exception("key:" + key);
		}
	}

	public long getLong(String key) throws Exception {
		try {
			return Long.parseLong(this.pro.getProperty(key));
		} catch (Exception e) {
			throw new Exception("key:" + key);
		}
	}

	public float getFloat(String key) throws Exception {
		try {
			return Float.parseFloat(this.pro.getProperty(key));
		} catch (Exception e) {
			throw new Exception("key:" + key);
		}
	}

	public boolean getBoolean(String key) throws Exception {
		try {
			return Boolean.parseBoolean(this.pro.getProperty(key));
		} catch (Exception e) {
			throw new Exception("key:" + key);
		}
	}

	public Set<Object> getAllKey() {
		return this.pro.keySet();
	}

	public Collection<Object> getAllValue() {
		return this.pro.values();
	}

	private Properties loadProperty(String filePath) throws Exception {
		FileInputStream fin = null;
		Properties pro = new Properties();
		try {
			fin = new FileInputStream(filePath);
			pro.load(fin);
		} catch (IOException e) {
			throw e;
		} finally {
			if (fin != null)
				fin.close();
		}

		return pro;
	}
}