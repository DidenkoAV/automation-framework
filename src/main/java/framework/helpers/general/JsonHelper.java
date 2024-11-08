package framework.helpers.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonHelper {

    @Getter
    @Setter
    public static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> T convertResponseToPojo(Class<T> clazz, String response) {
        try {
            return objectMapper.readValue(response, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing response: " + response, e);
        }
    }

    public static String convertObjectToString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting object to JSON string", e);
        }
    }

    public static boolean jsonHasValue(String key, String json) {
        JSONObject obj = new JSONObject(json);
        return obj.has(key);
    }

    public static JSONObject convertArrayToObject(JSONArray jsonArray) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonItem = jsonArray.getJSONObject(i);
            String key = String.valueOf(jsonItem.getInt("id"));
            jsonObject.put(key, jsonItem);
        }
        return jsonObject;
    }
}
