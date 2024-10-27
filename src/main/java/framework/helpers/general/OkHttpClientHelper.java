package framework.helpers.general;

import lombok.Getter;
import okhttp3.*;
public class OkHttpClientHelper {

    @Getter
    public static OkHttpClient client = new OkHttpClient();
}
