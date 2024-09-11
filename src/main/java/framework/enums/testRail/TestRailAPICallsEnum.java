package framework.enums.testRail;

import java.util.HashMap;
import java.util.Map;

public enum TestRailAPICallsEnum {
    GET_CASE("get_case/"),
    ADD_RESULT("add_result/");

    private final String api;
    private static final Map<String, TestRailAPICallsEnum> lookup = new HashMap<>();

    static {
        for (TestRailAPICallsEnum apiEnum : TestRailAPICallsEnum.values()) {
            lookup.put(apiEnum.api, apiEnum);
        }
    }

    TestRailAPICallsEnum(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public static TestRailAPICallsEnum get(String api) {
        TestRailAPICallsEnum apiEnum = lookup.get(api);
        if (apiEnum != null) {
            return apiEnum;
        }
        throw new IllegalArgumentException("Invalid API: " + api);
    }
}
