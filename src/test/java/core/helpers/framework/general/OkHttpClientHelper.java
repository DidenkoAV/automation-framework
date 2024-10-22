package core.helpers.framework.general;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import okhttp3.*;

import java.io.IOException;

import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

public class OkHttpClientHelper {

    @Getter
    public static OkHttpClient client = new OkHttpClient();

    public static RequestBody prepareRequestBody(String json){
        return RequestBody.create(json, MediaType.get(APPLICATION_JSON +"; charset=utf-8"));
    }

    public static String executeRequest(Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected HTTP code: " + response.code());
            }
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonObject handleResponse(Response response) {
        if (!response.isSuccessful()) {
            throw new RuntimeException("Error response: " + response.code() + " " + response.message());
        }

        try {
            String responseBody = response.body().string();
            return JsonParser.parseString(responseBody).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read response body: " + e.getMessage(), e);
        }
    }
}
