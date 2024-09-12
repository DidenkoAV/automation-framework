package listener.testrail;

import framework.enums.testrail.TestRailCaseStatusEnum;
import helpers.TestRailHelper;
import helpers.AllureHelper;
import io.qameta.allure.AllureId;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.List;

public class TestRailListener implements ITestListener {

    private final String runId = "1"; // Adjust to your TestRail run ID


    @Override
    public void onStart(ITestContext context) {
        // Optional: Initialize or reset state if needed
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        handleTestResult(result, TestRailCaseStatusEnum.PASSED);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        handleTestResult(result, TestRailCaseStatusEnum.FAILED);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //handleTestResult(result, TestRailCaseStatusEnum.SKIPPED);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optional: Handle if needed
    }

    @Override
    public void onFinish(ITestContext context) {
        // Optional: Handle if needed
    }

    private void handleTestResult(ITestResult result, TestRailCaseStatusEnum statusEnum) {
        String testRailId = getTestRailTestId(result);
        List<String> logs = AllureHelper.getAllureLogs(result);

        String logsString = String.join("\n", logs);
        TestRailHelper.setCaseStatusAndCommentByRunIdAndCaseId(runId, testRailId, statusEnum, logsString);
    }

    public static String getTestRailTestId(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        String testId = null;
        if (method.isAnnotationPresent(AllureId.class)) {
            AllureId allureIdAnnotation = method.getAnnotation(AllureId.class);
            testId = allureIdAnnotation.value();
            if (testId.isEmpty()) {
                throw new RuntimeException("AllureId annotation has been set but its value is empty.");
            }
        }
        return testId != null ? testId.replaceAll("\\D+", "") : "";
    }

}
