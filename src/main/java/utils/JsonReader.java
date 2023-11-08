package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonReader {
    public static Object[][] getJsonData(String JsonPath, String JsonData, int JsonAttributes) throws IOException, ParseException {
        Object object = new JSONParser().parse(new FileReader(JsonPath));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray jsonArray = (JSONArray) jsonObject.get(JsonData);

        Object[][] arr = new String[jsonArray.size()][JsonAttributes];
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object2 = (JSONObject) jsonArray.get(i);
            arr[i][0] = String.valueOf(object2.get("TaskName"));
            arr[i][1] = String.valueOf(object2.get("TaskDesc"));
        }
        return arr;
    }
}
