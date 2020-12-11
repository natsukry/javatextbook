package map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.storeClassInHashMap();
    }
    /**hashmap 线程不安全**/
    public void storeClassInHashMap(){
        HashMap hashMap = new HashMap<Person,String>();
        Person p1 = new Person(1,"1");
        hashMap.put(p1,"1");
        System.out.println(hashMap.get(p1));
    }

    /**线程安全,  collections 类的静态方法 synchronizeMap（）实现**/
    public void safeHashMap(){
        Map map = new HashMap<String,String>();
        Map safeMap = Collections.synchronizedMap(map);
    }

    /**ConcurrentHashMap 使用分段锁 线程安全***/
    public void concurrentHashMap(){
        Map<String,String> safeMap = new ConcurrentHashMap();
        safeMap.put("a","va");
    }
}

class Person{
    int height;
    String name;

    public Person(int height, String name) {
        this.height = height;
        this.name = name;
    }
}
