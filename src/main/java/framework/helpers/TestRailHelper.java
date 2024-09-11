package framework.helpers;

import framework.enums.testRail.TestRailCaseStatusEnum;
import framework.helpers.general.JsonHelper;
import framework.helpers.general.PropertiesReaderHelper;
import framework.integrations.testrail.APIClient;
import framework.tdo.testRail.AddResultForCaseResponseTDO;
import framework.tdo.testRail.CaseDetailsResponseTDO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static framework.constants.testRail.TestRailConstants.*;
import static framework.enums.testRail.TestRailAPICallsEnum.*;

public class TestRailHelper {
    public static final APIClient testRailClient = initClient();

    public static APIClient initClient(){
        PropertiesReaderHelper readerHelper = new PropertiesReaderHelper("init.properties");

        APIClient client = new APIClient(readerHelper.getProperty("testrail.url"));
        client.setUser(readerHelper.getProperty("testrail.user"));
        client.setPassword(readerHelper.getProperty("testrail.password"));
        return client;
    }


    public static CaseDetailsResponseTDO getCaseDetailsById(int caseId) {
            JSONObject jsonObject = testRailClient.sendGet(GET_CASE.getApi() + caseId);
            return JsonHelper.convertResponseToPojo(CaseDetailsResponseTDO.class,jsonObject.toString());
    }

    public static AddResultForCaseResponseTDO setCaseStatusByRunIdAndCaseId(int runId, int caseId, TestRailCaseStatusEnum statusEnum, String comment) {
        if (runId <= 0 || caseId <= 0) {
            throw new IllegalArgumentException("Run ID and Case ID must be positive integers.");
        }
        if (statusEnum == null) {
            throw new IllegalArgumentException("StatusEnum cannot be null.");
        }

        Map data = new HashMap<>();
        data.put(STATUS_ID, statusEnum.getStatus());

        String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
        JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
        return JsonHelper.convertResponseToPojo(AddResultForCaseResponseTDO.class,jsonObject.toString());
    }

    public static void setStatusToCasesByRunId(int runId,TestRailCaseStatusEnum statusEnum){
        String testCaseId;
        try  {
            JSONArray testsArrayList = getAllTestsByRunId(runId);
            System.out.println("Test Run  with id [" + runId + "] has [" + testsArrayList.length()+ "] tests");

            for (int i = 0; i < testsArrayList.length(); i++)    {
                JSONObject testCaseItem = (((JSONObject) testsArrayList.get(i)));
                testCaseId = testCaseItem.get(CASE_ID).toString();

                Map data = new HashMap();
                data.put(STATUS_ID, statusEnum.getStatus());

//                avoid too many requests 429
//                if (i % 50 == 0) {
//                    Thread.sleep(60000);
//                }

                Thread.sleep(2000);

                String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + testCaseId;
                testRailClient.sendPost(apiUrl, data);
                System.out.println("Case id [" + testCaseId + "] updated to [" + statusEnum.name() + "] status");

            }
        } catch (Exception e)  {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray getAllTestsByRunId(int runId){
        JSONObject getTestsJson = testRailClient.sendGet(GET_TESTS.getApi() + runId);
        JSONArray testsArrayList = getTestsJson.getJSONArray(TESTS);
        return testsArrayList;
    }

    public static void main(String[] args) {
        //setCaseStatusByRunIdAndCaseId(1,1,TestRailCaseStatusEnum.PASSED, "Changed to retest status");
        setStatusToCasesByRunId(1, TestRailCaseStatusEnum.PASSED);
    }

}
