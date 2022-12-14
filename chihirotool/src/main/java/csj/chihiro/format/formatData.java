package csj.chihiro.format;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class formatData {

    private static Gson gson=new Gson();
    public static void main(String[] args) {
        Map<String, Object> xcc=new HashMap<>();
        xcc.put("channel5G",new User());
        Map<String,String> channel5GMap=null;
        if(null!=xcc&&null!=xcc.get("channel5G")){
            String channel5G=gson.toJson(xcc.get("channel5G"));
            System.out.println("channel5G:"+channel5G);
            channel5GMap=gson.fromJson(channel5G,Map.class);
        }
        System.out.println("xcc:"+gson.toJson(xcc));
    }

    static class User {
        private String name="cs";
        private int age=3;
        private String sex="nan";
    }
}
