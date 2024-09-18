package framework.helpers.selenium;

import framework.annotations.InitTest;
import framework.enums.selenium.BrowserEnum;
import framework.helpers.general.PropertiesReaderHelper;
import framework.helpers.selenium.LoadBrowser;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterClass;

import java.lang.reflect.Method;

public class InitSeleniumTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup(Method method) {
        if (method.isAnnotationPresent(InitTest.class)) {
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
        InitTest initTest = method.getAnnotation(InitTest.class);
        String browser = initTest.browser();
        return browser;
    }


}
