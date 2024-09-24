package core.helpers.framework.testng;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AssertionHelper {

    @Step("Assert that the page title is: {expectedTitle}")
    public static void assertTitleIsExpected(WebDriver driver, String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "The page title did not match the expected title.");
    }
}
