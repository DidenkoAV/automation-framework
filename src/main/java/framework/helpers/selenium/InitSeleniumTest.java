package framework.helpers.selenium;

import framework.annotations.TestParams;
import framework.enums.selenium.BrowserEnum;
import framework.helpers.general.PropertiesReaderHelper;
import framework.listeners.TestRailListener;
import framework.tdo.testrail.TestConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static framework.constants.GeneralConstants.*;
import static framework.constants.testrail.TestRailConstants.RUNID;
import static framework.constants.testrail.TestRailConstants.SCENARIO;

@Listeners(TestRailListener.class)
public class InitSeleniumTest {
    protected WebDriver driver;

    @BeforeMethod
    @Parameters({BROWSER, RUNID, SCENARIO})
    public void setup(Method method,
                      @Optional(UNDEFINED) String browserParam,
                      @Optional(UNDEFINED) String runIdParam,
                      @Optional(UNDEFINED) String scenarioParam) {

        TestParams testParams = method.getAnnotation(TestParams.class);
        TestConfig testConfig = defineTestConfig(browserParam,runIdParam,scenarioParam,testParams);
        driver = defineDriver(testConfig.getBrowser());
        String url = defineBaseUrl();
        driver.get(url);
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static String defineBaseUrl() {
        PropertiesReaderHelper readerHelper = new PropertiesReaderHelper(INIT_PROPERTIES);
        return readerHelper.getProperty("selenium.url");
    }

    private static WebDriver defineDriver(String browser) {
        BrowserEnum browserEnum = BrowserEnum.get(browser);
        return switch (browserEnum) {
            case CHROME -> LoadBrowser.loadChrome();
            case FIREFOX -> LoadBrowser.loadFirefox();
        };
    }

    public TestConfig defineTestConfig(String browserParam, String runIdParam, String scenarioParam, TestParams testParams) {
        boolean isRunningFromXml = !UNDEFINED.equals(browserParam) && !UNDEFINED.equals(runIdParam);

        String browser = isRunningFromXml ? browserParam : testParams.browser();
        String runId = isRunningFromXml ? runIdParam : String.valueOf(testParams.runId());
        String scenario = isRunningFromXml ? scenarioParam : String.valueOf(testParams.scenario());

        return new TestConfig(browser, runId, scenario);
    }

}
