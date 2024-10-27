package framework.helpers.testrail;

import framework.enums.testrail.TestRailCaseStatusEnum;
import framework.helpers.general.JsonHelper;
import framework.helpers.general.PropertyHelper;
import framework.integrations.testrail.TestRailClient;
import framework.tdo.testrail.AddResultForCaseResponse;
import framework.tdo.testrail.CaseDetailsResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static framework.constants.GeneralConstants.*;
import static framework.constants.testrail.TestRailConstants.*;
import static framework.enums.testrail.TestRailAPICallsEnum.*;

public class TestRailHelper {
    public static final TestRailClient testRailClient = initClient();

    public static TestRailClient initClient() {
        String user = PropertyHelper.initAndGetProperty(INIT_PROPERTIES,TESTRAIL_USER);
        String password = PropertyHelper.initAndGetProperty(INIT_PROPERTIES,TESTRAIL_PASSWORD);
        return new TestRailClient(user,password);
    }


    public static CaseDetailsResponse getCaseDetailsById(String caseId) {
        JSONObject jsonObject = testRailClient.sendGet(GET_CASE.getApi() + caseId);
        return JsonHelper.convertResponseToPojo(CaseDetailsResponse.class, jsonObject.toString());
    }

    public static AddResultForCaseResponse setCaseStatusByRunIdAndCaseId(String runId, String caseId, TestRailCaseStatusEnum statusEnum) {

        if (statusEnum == null) {
            throw new RuntimeException("StatusEnum cannot be null.");
        }

        Map<Object, Object> data = new HashMap<>();
        data.put(STATUS_ID, statusEnum.getStatus());

        String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
        JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
        return JsonHelper.convertResponseToPojo(AddResultForCaseResponse.class, jsonObject.toString());
    }

    public static AddResultForCaseResponse setCaseStatusAndCommentByRunIdAndCaseId(String runId, String caseId, TestRailCaseStatusEnum statusEnum, String comment) {

        if (statusEnum == null) {
            throw new RuntimeException("Status cannot be null.");
        }

        Map data = new HashMap<>();
        data.put(STATUS_ID, statusEnum.getStatus());
        data.put(COMMENT, comment);

        String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
        JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
        return JsonHelper.convertResponseToPojo(AddResultForCaseResponse.class, jsonObject.toString());
    }

    public static JSONArray getAllTestsByRunId(int runId) {
        JSONObject getTestsJson = testRailClient.sendGet(GET_TESTS.getApi() + runId);
        return getTestsJson.getJSONArray(TESTS);
    }

    public static AddResultForCaseResponse setCaseStatusAndCommentByScenarioAndMethod(int runId, String method, int scenario, TestRailCaseStatusEnum statusEnum, String comment) {
        JSONArray testList = getAllTestsByRunId(runId);
        for (int i = 0; i < testList.length(); i++) {
            String methodByRun = testList.getJSONObject(i).get("custom_test_method").toString();
            String scenarioByRun = testList.getJSONObject(i).get("custom_scenario").toString();
            if (methodByRun.compareTo(method) == 0 && scenarioByRun.compareTo(String.valueOf(scenario)) == 0) {
                String caseId = testList.getJSONObject(i).get("case_id").toString();
                Map<Object, Object> data = new HashMap<>();
                data.put(STATUS_ID, statusEnum.getStatus());
                data.put(COMMENT, comment);

                String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
                JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
                return JsonHelper.convertResponseToPojo(AddResultForCaseResponse.class, jsonObject.toString());
            }
        }
        return null;
    }
}
