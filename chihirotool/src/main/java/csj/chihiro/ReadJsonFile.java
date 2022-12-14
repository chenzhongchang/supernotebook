package csj.chihiro;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;

public class ReadJsonFile {

    private static String filepath = Thread.currentThread().getContextClassLoader().getResource("config/xxgConfig.json").getPath();

    public static void main(String[] args) {
        String content=readFile(filepath).toString();
        content=content.replaceAll("\"","");
        System.out.println("content:"+content);
    }

    static JsonObject readFile(String path){
        JsonParser parser = new JsonParser();
        FileReader fileReader;
        try {

            String system = System.getProperties().get("os.name").toString().toUpperCase();
            if (!system.contains("WINDOW")) {
                path = "/home/app/conf/wireless.json";
                File file = new File(path);
                if (!file.exists())
                    path = "/usr/local/doublecom/app/conf/wireless.json";
            }
            File file = new File(path);
            fileReader = new FileReader(file);
            JsonObject jsonObject=parser.parse(fileReader).getAsJsonObject();
//            JsonArray jsonArray = parser.parse(fileReader).getAsJsonArray();
            System.out.println("jsonObject:"+jsonObject.toString());
            JsonElement jsonElement = jsonObject.get("5G");
            JsonElement jsonElement1 = jsonObject.get("2G");
            System.out.println("5G:"+jsonElement.toString());
            if(jsonElement1!=null)
                System.out.println("2G:"+jsonElement1.toString());
            return jsonObject;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
