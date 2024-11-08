package framework.helpers.testrail;

import framework.enums.testrail.TestRailCaseStatusEnum;
import framework.helpers.general.AnnotationHelper;
import framework.helpers.general.JsonHelper;
import framework.helpers.general.PropertyHelper;
import framework.tdo.testrail.AddResultForCaseResponse;
import framework.tdo.testrail.CaseDetailsResponse;
import framework.tdo.testrail.ScenarioRunIdConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static framework.constants.GeneralConstants.*;
import static framework.constants.testrail.TestRailConstants.*;
import static framework.enums.testrail.TestRailAPICallsEnum.*;
import static framework.helpers.testng.DataProviderHelper.isRunningViaXml;
import static framework.listeners.TestRailListener.ALL_SCENARIOS;

public class TestRailHelper {
    public static final TestRailAPIHelper TEST_RAIL_API_HELPER = initClient();

    public static TestRailAPIHelper initClient() {
        String user = PropertyHelper.initAndGetProperty(INIT_PROPERTIES,TESTRAIL_USER);
        String password = PropertyHelper.initAndGetProperty(INIT_PROPERTIES,TESTRAIL_PASSWORD);
        return new TestRailAPIHelper(user,password);
    }


    public static CaseDetailsResponse getCaseDetailsById(String caseId) {
        JSONObject jsonObject = TEST_RAIL_API_HELPER.sendGet(GET_CASE.getApi() + caseId);
        return JsonHelper.convertResponseToPojo(CaseDetailsResponse.class, jsonObject.toString());
    }

    public static AddResultForCaseResponse setCaseStatusByRunIdAndCaseId(String runId, String caseId, TestRailCaseStatusEnum statusEnum) {

        if (statusEnum == null) {
            throw new RuntimeException("StatusEnum cannot be null.");
        }

        Map<Object, Object> data = new HashMap<>();
        data.put(STATUS_ID, statusEnum.getStatus());

        String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
        JSONObject jsonObject = TEST_RAIL_API_HELPER.sendPost(apiUrl, data);
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
        JSONObject jsonObject = TEST_RAIL_API_HELPER.sendPost(apiUrl, data);
        return JsonHelper.convertResponseToPojo(AddResultForCaseResponse.class, jsonObject.toString());
    }

    public static JSONArray getAllTestsByRunId(int runId) {
        JSONObject getTestsJson = TEST_RAIL_API_HELPER.sendGet(GET_TESTS.getApi() + runId);
        return getTestsJson.getJSONArray(TESTS);
    }

    public static AddResultForCaseResponse setCaseStatusAndCommentByScenarioAndMethod(int runId, String method, int scenario, TestRailCaseStatusEnum statusEnum, String comment) {
        JSONArray testList = getAllTestsByRunId(runId);
        for (int i = 0; i < testList.length(); i++) {
            String methodByRun = testList.getJSONObject(i).get(CUSTOM_TEST_METHOD).toString();
            String scenarioByRun = testList.getJSONObject(i).get(CUSTOM_SCENARIO).toString();
            if (methodByRun.compareTo(method) == 0 && scenarioByRun.compareTo(String.valueOf(scenario)) == 0) {
                String caseId = testList.getJSONObject(i).get(CASE_ID).toString();
                Map<Object, Object> data = new HashMap<>();
                data.put(STATUS_ID, statusEnum.getStatus());
                data.put(COMMENT, comment);

                String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
                JSONObject jsonObject = TEST_RAIL_API_HELPER.sendPost(apiUrl, data);
                return JsonHelper.convertResponseToPojo(AddResultForCaseResponse.class, jsonObject.toString());
            }
        }
        return null;
    }

    public static ScenarioRunIdConfig defineScenarioAndRunId(ITestContext context, Method method) {
        int scenario;
        int runId;

        if (isRunningViaXml(context)) {
            String scenarioParam = context.getCurrentXmlTest().getParameter(SCENARIO);
            String runIdParam = context.getCurrentXmlTest().getParameter(RUNID);

            if (scenarioParam == null) {
                scenarioParam = String.valueOf(ALL_SCENARIOS);
            }

            scenario = Integer.parseInt(scenarioParam);
            runId = (runIdParam != null) ? Integer.parseInt(runIdParam) : -1;
        } else {
            scenario = AnnotationHelper.getScenarioFromTestParams(method);
            runId = AnnotationHelper.getRunIdFromTestParams(method);
        }

        return new ScenarioRunIdConfig(scenario, runId);
    }
}
