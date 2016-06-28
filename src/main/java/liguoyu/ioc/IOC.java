package liguoyu.ioc;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IOC的核心类
 * 思路：控制反转--暂时先只支持注解的反转
 * 启动步骤:1.扫描src下的所有文件 读取对应的注解
 *         2.自动将添加注解的类以IOCClass的方式存储 使用的时候反射的方式初始化
 *
 *
 * Created by liguoyu@58.com on 2016/6/28.
 */
public class IOC {

    Map<String,IOCBean> beanMap = new ConcurrentHashMap<String, IOCBean>();

    public static void main(String[] args) throws Exception {
//        System.out.println(System.getProperty("user.dir"));
//        System.out.println(System.getProperties().toString());
//        String packageName = "";
//        File root = new File(System.getProperty("user.dir") + "\\src");
//        for (Annotation annotation : Bean.class.getAnnotations()) {
//            System.out.println(annotation.annotationType().getSimpleName());
//        }
        System.out.println();
        new IOC().loop();
    }

    /**
     * 初始化
     */
    private void init() throws Exception {
        loop();//先获取src下所有带@Bean的类
        //TODO 再做填充
    }

    /**
     * 递归的方式获取src下的所有类
     * @throws Exception
     */
    private void loop() throws Exception{
        File root = new File(System.getProperty("user.dir") + "\\src");
        loop(root);
    }

    private void loop(File folder) throws Exception{
        loop(folder,"");
    }

    private void loop(File folder, String packageName) throws Exception {
        File[] files = folder.listFiles();
        packageName = packageName.replaceAll("main\\.","").replaceAll("java\\.","");
        for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
            File file = files[fileIndex];
            if (file.isDirectory()) {
                loop(file, packageName + file.getName() + ".");
            } else {
                listMethodNames(file.getName(), packageName);
            }
        }
    }
    private void listMethodNames(String filename, String packageName) {
        try {
            String name = filename.substring(0, filename.length() - 5);
            Class obj = Class.forName(packageName + name);
            for (Annotation annotation : obj.getAnnotations()) {
                if(annotation instanceof Bean){
                    iocBean = getIocBean();
                    iocBean.name= obj.getName();
                    beanMap.put(iocBean.name, iocBean);
                    System.out.println("class load---classname:"+obj.getName()+"--class simplename:"+iocBean.name);
                }
            }
            iocBean = null;//help gc
        } catch (Exception e) {
            System.out.println("exception = " + e.getLocalizedMessage());
        }
    }

    private IOCBean iocBean;

    public IOCBean getIocBean(){
        if(iocBean == null){
            return new IOCBean();
        }else{
            return iocBean;
        }
    }

    public class IOCBean {
        String name;
    }

}  
