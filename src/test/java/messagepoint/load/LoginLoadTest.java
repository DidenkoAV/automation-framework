package messagepoint.load;

import framework.annotations.TestParams;
import framework.helpers.selenium.LoadBrowser;
import framework.helpers.testng.AssertionHelper;
import framework.helpers.testng.DataProviderHelper;
import messagepoint.helpers.LoginHelper;
import messagepoint.tdo.umh.LoginTdo;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static framework.constants.GeneralConstants.CSV_DATA;


/**
 * Authorization tests
 */
public class LoginLoadTest {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        driver.set(LoadBrowser.loadChrome());
    }

    @Test()
    public void loadLogin() {
        driver.get().get("https://ta241avi.messagepoint.com/mp/signin.jsp");
        HashMap<String,String> data = getTestData();
        
        LoginTdo loginTdo = LoginHelper.setupLoginTdo(data);
        LoginHelper.login(driver.get(), loginTdo);
        AssertionHelper.verifyEquals(Objects.requireNonNull(driver.get().getTitle()), loginTdo.getTitle(),"Title is: " + loginTdo.getTitle());
    }

    @AfterMethod
    public void closeDriver(){
        if(driver!=null){
            driver.get().close();
        }
    }

    public static HashMap<String, String> getTestData() {
        HashMap<String, String> testData = new HashMap<>();

        testData.put("messagepoint", "automation#prinova");
        testData.put("user", "automation");
        testData.put("password", "Prinova1");

        return testData;
    }
}
