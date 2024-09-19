package core.helpers.framework.testrail;

import core.enums.testrail.TestRailCaseStatusEnum;
import core.helpers.framework.general.JsonHelper;
import core.helpers.framework.general.PropertiesReaderHelper;
import core.integrations.testrail.APIClient;
import core.tdo.testrail.AddResultForCaseResponseTDO;
import core.tdo.testrail.CaseDetailsResponseTDO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static core.constants.testrail.TestRailConstants.*;
import static core.enums.testrail.TestRailAPICallsEnum.*;

public class TestRailHelper {
    public static final APIClient testRailClient = initClient();

    public static APIClient initClient(){
        PropertiesReaderHelper readerHelper = new PropertiesReaderHelper("init.properties");

        APIClient client = new APIClient(readerHelper.getProperty("testrail.url"));
        client.setUser(readerHelper.getProperty("testrail.user"));
        client.setPassword(readerHelper.getProperty("testrail.password"));
        return client;
    }


    public static CaseDetailsResponseTDO getCaseDetailsById(String caseId) {
            JSONObject jsonObject = testRailClient.sendGet(GET_CASE.getApi() + caseId);
            return JsonHelper.convertResponseToPojo(CaseDetailsResponseTDO.class,jsonObject.toString());
    }

    public static AddResultForCaseResponseTDO setCaseStatusByRunIdAndCaseId(String runId, String caseId, TestRailCaseStatusEnum statusEnum) {

        if (statusEnum == null) {
            throw new IllegalArgumentException("StatusEnum cannot be null.");
        }

        Map data = new HashMap<>();
        data.put(STATUS_ID, statusEnum.getStatus());

        String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
        JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
        return JsonHelper.convertResponseToPojo(AddResultForCaseResponseTDO.class,jsonObject.toString());
    }

    public static AddResultForCaseResponseTDO setCaseStatusAndCommentByRunIdAndCaseId(String runId, String caseId, TestRailCaseStatusEnum statusEnum, String comment) {

        if (statusEnum == null) {
            throw new RuntimeException("Status cannot be null.");
        }

        Map data = new HashMap<>();
        data.put(STATUS_ID, statusEnum.getStatus());
        data.put(COMMENT,comment);

        String apiUrl = ADD_RESULT_FOR_CASE.getApi() + runId + "/" + caseId;
        JSONObject jsonObject = testRailClient.sendPost(apiUrl, data);
        return JsonHelper.convertResponseToPojo(AddResultForCaseResponseTDO.class,jsonObject.toString());
    }

    public static void setStatusToCasesByRunId(String runId,TestRailCaseStatusEnum statusEnum){
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

    public static JSONArray getAllTestsByRunId(String runId){
        JSONObject getTestsJson = testRailClient.sendGet(GET_TESTS.getApi() + runId);
        JSONArray testsArrayList = getTestsJson.getJSONArray(TESTS);
        return testsArrayList;
    }

    public static void main(String[] args) {
        //setCaseStatusByRunIdAndCaseId(1,1,TestRailCaseStatusEnum.PASSED, "Changed to retest status");
        setStatusToCasesByRunId("1", TestRailCaseStatusEnum.PASSED);
    }

}
