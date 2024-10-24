package messagepoint.ui.smoke;

import framework.annotations.TestParams;
import framework.helpers.selenium.InitSeleniumTest;
import framework.helpers.testng.AssertionHelper;
import framework.helpers.testng.DataProviderHelper;
import messagepoint.helpers.LoginHelper;
import messagepoint.tdo.umh.LoginTdo;
import org.testng.annotations.Test;
import java.util.HashMap;


/**
 * Authorization tests
 */
public class AuthorizationTests extends InitSeleniumTest {

    @TestParams(browser = "chrome", csvPath = "login.csv", runId = 1, scenario = 2)
    @Test(dataProvider = "csvData", dataProviderClass = DataProviderHelper.class)
    public void login(HashMap<String, String> data) {
        LoginTdo loginTdo = LoginHelper.setupLoginTdo(data);

        LoginHelper.login(driver, loginTdo);

        AssertionHelper.verifyEquals(driver.getTitle(), loginTdo.getTitle(),"Title is: " + loginTdo.getTitle());

    }
}
