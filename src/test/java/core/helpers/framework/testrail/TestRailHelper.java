package core.helpers.framework.testrail;

import core.dto.testrail.TestListByRunIdDTO;
import core.enums.testrail.TestRailCaseStatusEnum;
import core.helpers.framework.general.JsonHelper;
import core.helpers.framework.general.PropertiesReaderHelper;
import core.integrations.testrail.APIClient;
import core.dto.testrail.AddResultForCaseResponseDTO;
import core.dto.testrail.CaseDetailsResponseDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.constants.testrail.TestRailConstants.*;
import static core.enums.testrail.TestRailAPICallsEnum.*;

public class TestRailHelper {
    public static final APIClient testRailClient = initClient();

    public static APIClient initClient() {
        PropertiesReaderHelper readerHelper = new PropertiesReaderHelper("init.properties");

        APIClient client = new APIClient(readerHelper.getProperty("testrail.url"));
        client.setUser(readerHelper.getProperty("testrail.user"));
        client.setPassword(readerHelper.getProperty("testrail.password"));
        return client;
    }


    public static CaseDetailsResponseDTO getCaseDetailsById(String caseId) {
        JSONObject jsonObject = testRailClient.sendGet(GET_CASE.getApi() + caseId);
        return JsonHelper.convertResponseToPojo(CaseDetailsResponseDTO.class, jsonObject.toString());
    }

    public static AddResultForCaseResponseDTO setCaseStatusByRunIdAndCaseId(String runId, String caseId, TestRailCaseStatusEnum statusEnum) {

        if (statusEnum == null) {
            throw new IllegalArgumentException("StatusEnum cannot be null.");
        }

        Map data = new HashMap<>();
        data.put(STATUS_ID, statusEnum.getStatus());

        String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
        JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
        return JsonHelper.convertResponseToPojo(AddResultForCaseResponseDTO.class, jsonObject.toString());
    }

    public static AddResultForCaseResponseDTO setCaseStatusAndCommentByRunIdAndCaseId(String runId, String caseId, TestRailCaseStatusEnum statusEnum, String comment) {

        if (statusEnum == null) {
            throw new RuntimeException("Status cannot be null.");
        }

        Map data = new HashMap<>();
        data.put(STATUS_ID, statusEnum.getStatus());
        data.put(COMMENT, comment);

        String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
        JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
        return JsonHelper.convertResponseToPojo(AddResultForCaseResponseDTO.class, jsonObject.toString());
    }

    public static JSONArray getAllTestsByRunId(int runId) {
        JSONObject getTestsJson = testRailClient.sendGet(GET_TESTS.getApi() + runId);
        JSONArray testsArrayList = getTestsJson.getJSONArray(TESTS);
        return testsArrayList;
    }

    public static AddResultForCaseResponseDTO setCaseStatusAndCommentByScenarioAndMethod(int runId, String method, int scenario, TestRailCaseStatusEnum statusEnum, String comment) {
        JSONArray testList = getAllTestsByRunId(runId);
        for (int i = 0; i < testList.length(); i++) {
            String methodByRun = testList.getJSONObject(i).get("custom_test_method").toString();
            String scenarioByRun = testList.getJSONObject(i).get("custom_scenario").toString();
            if (methodByRun.compareTo(method) == 0 && scenarioByRun.compareTo(String.valueOf(scenario)) == 0) {
                String caseId = testList.getJSONObject(i).get("case_id").toString();
                Map data = new HashMap<>();
                data.put(STATUS_ID, statusEnum.getStatus());
                data.put(COMMENT, comment);

                String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
                JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
                return JsonHelper.convertResponseToPojo(AddResultForCaseResponseDTO.class, jsonObject.toString());
            }
        }
        return null;
    }

    public static String formatComments(List<String> steps) {
        StringBuilder formattedComment = new StringBuilder();

        for (String step : steps) {
            boolean containsAssert = step.toLowerCase().contains("assert");
            String formattedStep = step.replace("passed", "<span style='color: green;'>passed</span>");

            if (containsAssert) {
                formattedStep = "<strong>" + formattedStep + "</strong>";
            }

            formattedComment.append(formattedStep).append("<br>");
        }

        return formattedComment.toString();
    }
}
