package framework.helpers.testng;

import framework.helpers.log.LogHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class AssertionHelper {

    private static WebDriver driver;
    private static SoftAssert softAssert = new SoftAssert();
    private static int totalAssertions;
    private static int failedAssertions;

    public AssertionHelper(WebDriver driver) {
        AssertionHelper.driver = driver;
        softAssert = new SoftAssert();
        totalAssertions = 0;
        failedAssertions = 0;
    }

    @Step("Assert that condition is true: {message}")
    public static void verifyTrue(boolean isTrue, String message) {
        evaluateAssertion(isTrue, message, true);
    }

    @Step("Assert that condition is false: {message}")
    public static void verifyFalse(boolean isFalse, String message) {
        evaluateAssertion(!isFalse, message, false);
    }

    @Step("Assert that {actual} equals {expected}: {message}")
    public static void verifyEquals(Object expected, Object actual, String message) {
        boolean result = expected.equals(actual);
        evaluateAssertion(result, message, true);
        softAssert.assertEquals(actual, expected, message);
    }

    @Step("Assert that {actual} does not equal {expected}: {message}")
    public static void verifyNotEquals(Object expected, Object actual, String message) {
        boolean result = !expected.equals(actual);
        evaluateAssertion(result, message, false);
        softAssert.assertNotEquals(actual, expected, message);
    }

    public static void assertAll() {
        softAssert.assertAll();
    }


    public static void getStatistics() {
        int passedAssertions = totalAssertions - failedAssertions;
        String result =  String.format("Total Assertions: %d | Passed: %d | Failed: %d",
                totalAssertions, passedAssertions, failedAssertions);
        LogHelper.verifyStep(result);
    }

    private static void evaluateAssertion(boolean condition, String message, boolean isTrueCheck) {
        totalAssertions++;
        if (!condition) {
            failedAssertions++;
            if (driver != null) {
                LogHelper.verifyStep("AssertFailure: " + message);
            }
        }
        LogHelper.verifyStep( message + " | Result: " + (condition ? "PASSED" : "FAILED"));
        if (isTrueCheck) {
            softAssert.assertTrue(condition, message);
        } else {
            softAssert.assertFalse(!condition, message);
        }
    }
}
