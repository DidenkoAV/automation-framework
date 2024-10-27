package framework.integrations.testrail;

import framework.enums.general.HttpEnum;
import framework.helpers.general.JsonHelper;
import framework.helpers.general.OkHttpHelper;
import framework.helpers.general.PropertiesReaderHelper;
import lombok.Setter;
import okhttp3.*;
import org.json.JSONObject;

import static framework.constants.GeneralConstants.INIT_PROPERTIES;
import static framework.constants.testrail.TestRailConstants.ADD_ATTACHMENT;
import static framework.enums.general.HttpEnum.*;

@Setter
public class TestRailClient {
	private String user;
	private String password;

	public TestRailClient(String user, String password) {
		this.user=user;
		this.password=password;
	}

	public JSONObject sendGet(String url) {
		return sendRequest(GET, url, null);
	}

	public JSONObject sendPost(String url, Object data) {
		return sendRequest(POST, url, data);
	}

	private JSONObject sendRequest(HttpEnum method, String url, Object data) {
		String baseUrl = new PropertiesReaderHelper(INIT_PROPERTIES).getProperty("testrail.url");
		Request.Builder requestBuilder = OkHttpHelper.prepareRequestBuilder(user, password, baseUrl + url);

		switch (method) {
			case GET -> requestBuilder.get();
			case POST -> {
				if (url.startsWith(ADD_ATTACHMENT) && data instanceof String attachmentPath) {
					requestBuilder.post(OkHttpHelper.createMultipartBody(attachmentPath));
				} else {
					String json = JsonHelper.convertObjectToString(data);
					requestBuilder.post(RequestBody.create(json, MediaType.parse("application/json")));
				}
			}
			case PUT, DELETE -> throw new RuntimeException("Method not implemented: " + method);
			default -> throw new RuntimeException("Unsupported HTTP method: " + method);
		}

		return OkHttpHelper.executeRequest(requestBuilder);
	}
}
