
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

public class Test {

    public static void main(String args[]){
        Map<String, String> map = new HashMap<>();
        map.put("id","1");
        map.put("paramA","2");
        List<Map<String, String>> list = new ArrayList<>();
        list.add(map);
    }

    public static void print(String str) {
        System.out.println(str);
    }
}
