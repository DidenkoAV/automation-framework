package core.helpers.framework.selenium;

import core.annotations.TestParams;
import core.enums.selenium.BrowserEnum;
import core.helpers.framework.general.AnnotationHelper;
import core.helpers.framework.general.PropertiesReaderHelper;
import core.listeners.TestRailListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners(TestRailListener.class)
public class InitSeleniumTest {
    private static final String UNDEFINED = "undefined";
    protected WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "runId", "scenario"})
    public void setup(Method method, @Optional(UNDEFINED) String browserParam, @Optional(UNDEFINED) String runIdParam, @Optional(UNDEFINED) String scenarioParam) {
        String browser;
        String runId;
        String scenario;
        String csvFilePath;

        TestParams testParams = method.getAnnotation(TestParams.class);
        csvFilePath = testParams.csvPath();

        boolean isRunningFromXml = !UNDEFINED.equals(browserParam) && !UNDEFINED.equals(runIdParam) && !UNDEFINED.equals(scenarioParam);

        if (isRunningFromXml) {
            browser = browserParam;
            runId = runIdParam;
            scenario = scenarioParam;
        } else {
            browser = testParams.browser();
            runId = String.valueOf(testParams.runId());
            scenario = String.valueOf(testParams.scenario());
        }

        driver = defineDriver(browser);
        String baseUrl = defineBaseUrl();
        driver.get(baseUrl);

        System.out.println("Running test with Run ID: " + runId + " and Scenario: " + scenario);
    }



    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static String defineBaseUrl(){
        PropertiesReaderHelper readerHelper = new PropertiesReaderHelper("init.properties");
        String baseUrl = readerHelper.getProperty("selenium.url");
        return baseUrl;
    }

    private static WebDriver defineDriver(String browser){
        WebDriver driver;
        BrowserEnum browserEnum = BrowserEnum.get(browser);
        switch (browserEnum) {
            case CHROME:
                driver = LoadBrowser.loadChrome();
                return driver;
            case FIREFOX:
                driver = LoadBrowser.loadFirefox();
                return driver;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
    }
}
