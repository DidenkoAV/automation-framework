package core.helpers.framework.selenium;

import core.annotations.TestParams;
import core.enums.selenium.BrowserEnum;
import core.helpers.framework.general.AnnotationHelper;
import core.helpers.framework.general.PropertiesReaderHelper;
import core.helpers.framework.testng.DataProviderHelper;
import core.listeners.TestRailListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static core.constants.testrail.GeneralConstants.*;

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

        boolean isRunningFromXml = !UNDEFINED.equals(browserParam) && !UNDEFINED.equals(runIdParam);

        String browser = isRunningFromXml ? browserParam : testParams.browser();
        String runId = isRunningFromXml ? runIdParam : String.valueOf(testParams.runId());
        String scenario = isRunningFromXml ? scenarioParam : String.valueOf(testParams.scenario());

        driver = defineDriver(browser);
        driver.get(defineBaseUrl());

        System.out.println("Running test with Run ID: " + runId + " and Scenario: " + scenario);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static String defineBaseUrl() {
        PropertiesReaderHelper readerHelper = new PropertiesReaderHelper("init.properties");
        return readerHelper.getProperty("selenium.url");
    }

    private static WebDriver defineDriver(String browser) {
        BrowserEnum browserEnum = BrowserEnum.get(browser);
        switch (browserEnum) {
            case CHROME:
                return LoadBrowser.loadChrome();
            case FIREFOX:
                return LoadBrowser.loadFirefox();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
