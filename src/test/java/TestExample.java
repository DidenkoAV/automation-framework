import framework.annotations.InitTest;
import framework.helpers.selenium.InitSeleniumTest;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class TestExample extends InitSeleniumTest {

    @Test
    @InitTest(browser = "chrome")
    @AllureId("T2") // Test case ID for TestRail
    @Description("test1")
    public void testExample() {
        System.out.println(driver.getTitle());
    }


}
