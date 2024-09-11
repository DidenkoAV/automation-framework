package framework.helpers;

import framework.helpers.general.JsonHelper;
import framework.helpers.general.PropertiesReaderHelper;
import framework.integrations.testrail.APIClient;
import framework.tdo.testRail.CaseDetailsResponseTDO;
import org.json.JSONObject;

import static framework.enums.testRail.TestRailAPICallsEnum.GET_CASE;

public class TestRailHelper {
    public static final APIClient testRailClient = initClient();

    public static APIClient initClient(){
        PropertiesReaderHelper readerHelper = new PropertiesReaderHelper("init.properties");

        APIClient client = new APIClient(readerHelper.getProperty("testrail.url"));
        client.setUser(readerHelper.getProperty("testrail.user"));
        client.setPassword(readerHelper.getProperty("testrail.password"));
        return client;
    }


    public static CaseDetailsResponseTDO getCaseDetailsById(int id) {
            JSONObject jsonObject = testRailClient.sendGet(GET_CASE.getApi() + id);
            return JsonHelper.convertResponseToPojo(CaseDetailsResponseTDO.class,jsonObject.toString());
    }

    public static void main(String[] args) {

        CaseDetailsResponseTDO caseDetailsResponseTDO = getCaseDetailsById(1);
        System.out.println(caseDetailsResponseTDO.getCaseStatusId());
    }

}
