package liguoyu.common;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {

	private static final Logger log = Logger.getLogger(SerializeUtil.class);
	
    /**
     * 
     * @Description: 序列化
     * @param @param object
     * @param @return
     * @return byte[]
     * @throws
     * 
     * @date 2015年5月6日 下午5:46:38
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.close();
            baos.close();
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        	log.error("Object serialize ERROR", e);
        }
        return null;
    }

    /**
     *
     * @Description: 反序列化
     * @param @param bytes
     * @param @return
     * @return Object
     * @throws
     * 
     * @date 2015年5月6日 下午5:50:04
     */
    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.close();
            bais.close();
            return ois.readObject();
        } catch (Exception e) {
        	log.error("Object deserialize ERROR", e);
        }
        return null;
    }
}
