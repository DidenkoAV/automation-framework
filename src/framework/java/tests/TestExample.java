package tests;

import core.annotations.TestData;
import core.annotations.TestParams;
import core.helpers.framework.selenium.InitSeleniumTest;
import core.helpers.framework.testng.DataProviderHelper;
import core.helpers.tests.umh.LoginHelper;
import core.tdo.umh.LoginTdo;
import io.qameta.allure.AllureId;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class TestExample extends InitSeleniumTest {

    @TestParams(browser = "chrome")
    @TestData(path = "src/framework/java/tests/data/TestExample.csv")
    @AllureId("T2") // Test case ID for TestRail
    @Test(dataProvider = "csvData", dataProviderClass = DataProviderHelper.class)
    public void testExample(HashMap<String, String> data) {
        LoginTdo loginTdo = LoginHelper.setupLoginTdo(data);

        LoginHelper.login(driver,loginTdo);

        Assert.assertEquals(driver.getTitle(), loginTdo.getTitle());
    }


}
