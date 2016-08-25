package liguoyu.guava.collectTest;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;

import java.util.Map;

/**
 * Created by liguoyu@58.com on 2016/7/22.
 */
public class CollectTest {
    public static void main(String[] args){
        //不可变map
        //构建好这个map后 这个map无法通过put/remove方法改变对应的值
        //构建后的返回值为RegularImmutableMap/SingletonImmutableBiMap 都是final的class
        ImmutableMap.Builder<String,String> builder = ImmutableMap.builder();
        ImmutableMap<String,String> immutableMap=builder.put("1","1").put("2","2").put("3","3").build();


        //将map转化成多个Map拼在一起的类 方便遍历 并方便把这个map转成list做其他操作
        ImmutableSetMultimap<String,String> multimap =  immutableMap.asMultimap();
        for (Map.Entry<String, String> stringStringEntry : multimap.entries()) {
            System.out.println(stringStringEntry.getKey()+":"+stringStringEntry.getValue());
        }


    }
}  
