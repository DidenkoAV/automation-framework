package framework.helpers.general;

import lombok.Getter;
import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static framework.constants.GeneralConstants.ATTACHMENT;
import static framework.constants.GeneralConstants.AUTHORIZATION;
import static framework.enums.general.ContentTypeEnum.OCTET_STREAM;

public class OkHttpHelper {

    @Getter
    public static OkHttpClient client = new OkHttpClient();

    public static Request.Builder prepareRequestBuilder(String user, String password,String url){
        return new Request.Builder()
                .url(url)
                .addHeader(AUTHORIZATION, Credentials.basic(user, password));
    }

    public static MultipartBody createMultipartBody(String filePath) {
        File file = new File(filePath);
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(ATTACHMENT, file.getName(),
                        RequestBody.create(file, MediaType.parse(OCTET_STREAM.getContentType())))
                .build();
    }

    public static JSONObject executeRequest(Request.Builder requestBuilder)  {
        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            int status = response.code();
            if (status != 200) {
                throw new RuntimeException("API response:" + status + " (" + response.message() + ")");
            }
            assert response.body() != null;
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
    }
}

}
