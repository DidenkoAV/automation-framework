package framework.helpers.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;

public class JsonHelper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String getJsonParameterFromJsonObject(String paramName, JSONObject jsonObj) {
        return jsonObj.optString(paramName, null);
    }

    public static JSONObject convertXMLStringToJSONObject(String xmlStr) {
        return XML.toJSONObject(xmlStr);
    }

    public static <T> T convertResponseToPojo(Class<T> clazz, String response) {
        try {
            return OBJECT_MAPPER.readValue(response, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing response: " + response, e);
        }
    }

    public static String convertObjectToString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting object to JSON string", e);
        }
    }

    public static boolean jsonHasValue(String key, String json) {
        JSONObject obj = new JSONObject(json);
        return obj.has(key);
    }
}
