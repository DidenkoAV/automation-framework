package framework.enums.testrail;

import java.util.Arrays;

public enum TestRailAPICallsEnum {
    GET_CASE("get_case/"),
    GET_TESTS("get_tests/"),
    ADD_RESULT("add_result/"),
    ADD_RESULT_FOR_CASE("add_result_for_case/");

    private final String api;

    TestRailAPICallsEnum(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public static TestRailAPICallsEnum get(String api) {
        return Arrays.stream(values())
                .filter(apiEnum -> apiEnum.api.equals(api))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid API: " + api));
    }
}
