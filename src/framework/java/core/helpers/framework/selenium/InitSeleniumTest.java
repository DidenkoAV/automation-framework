package core.helpers.framework.selenium;

import core.annotations.TestParams;
import core.enums.selenium.BrowserEnum;
import core.helpers.framework.general.PropertiesReaderHelper;
import core.listeners.TestRailListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;

@Listeners(TestRailListener.class)
public class InitSeleniumTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup(Method method) {
        if (method.isAnnotationPresent(TestParams.class)) {
            String browser = parseBrowserFromAnnotation(method);

            BrowserEnum browserEnum = BrowserEnum.get(browser);

            switch (browserEnum) {
                case CHROME:
                    driver = LoadBrowser.loadChrome();
                    break;
                case FIREFOX:
                    driver = LoadBrowser.loadFirefox();
                    break;
                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }

            PropertiesReaderHelper propertiesReaderHelper = new PropertiesReaderHelper("init.properties");
            String baseUrl = propertiesReaderHelper.getProperty("selenium.url");

            driver.get(baseUrl);
        } else {
            throw new RuntimeException("Please setup annotation @InitTest with browser to test method");
    }

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static String parseBrowserFromAnnotation(Method method){
        TestParams testParams = method.getAnnotation(TestParams.class);
        String browser = testParams.browser();
        return browser;
    }


}
