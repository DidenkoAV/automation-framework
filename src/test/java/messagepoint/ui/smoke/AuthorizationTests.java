package messagepoint.ui.smoke;

import framework.annotations.TestParams;
import framework.runners.SeleniumRunner;
import framework.helpers.testng.AssertionHelper;
import framework.helpers.testng.DataProviderHelper;
import messagepoint.helpers.LoginHelper;
import messagepoint.tdo.umh.LoginTdo;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Objects;

import static framework.constants.GeneralConstants.CSV_DATA;


/**
 * Authorization tests
 */
public class AuthorizationTests extends SeleniumRunner {

    @TestParams(browser = "chrome", csvPath = "login.csv", runId = 1, scenario = 2)
    @Test(dataProvider = CSV_DATA, dataProviderClass = DataProviderHelper.class)
    public void login(HashMap<String, String> data) {
        LoginTdo loginTdo = LoginHelper.setupLoginTdo(data);
        LoginHelper.login(driver, loginTdo);
        AssertionHelper.verifyEquals(Objects.requireNonNull(driver.getTitle()), loginTdo.getTitle(),"Title is: " + loginTdo.getTitle());
    }
}
