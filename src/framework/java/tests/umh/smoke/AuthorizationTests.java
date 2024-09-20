package tests.umh.smoke;

import core.annotations.TestParams;
import core.helpers.framework.selenium.InitSeleniumTest;
import core.helpers.framework.testng.AssertionHelper;
import core.helpers.framework.testng.DataProviderHelper;
import core.helpers.tests.umh.LoginHelper;
import core.dto.umh.LoginDto;
import org.testng.annotations.Test;
import java.util.HashMap;


/**
 * Authorization tests
 */
public class AuthorizationTests extends InitSeleniumTest {

    @TestParams(browser = "chrome", csvPath = "data/login.csv", runId = 1, scenario = 2)
    @Test(dataProvider = "csvData", dataProviderClass = DataProviderHelper.class)
    public void login(HashMap<String, String> data) {
        LoginDto loginDto = LoginHelper.setupLoginTdo(data);

        LoginHelper.login(driver, loginDto);

        AssertionHelper.assertTitleIsExpected(driver,loginDto.getTitle());
    }
}
