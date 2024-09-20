package core.listeners;

import core.enums.testrail.TestRailCaseStatusEnum;
import core.helpers.framework.testrail.TestRailHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.List;

import static core.helpers.framework.allure.AllureHelper.getAllureLogs;
import static core.helpers.framework.testng.TestNgHelper.getTestRailTestId;

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
        List<String> logs = getAllureLogs(result);

        String logsString = formatComments(logs);
        TestRailHelper.setCaseStatusAndCommentByRunIdAndCaseId(runId, testRailId, statusEnum, logsString);
    }

    private static String formatComments(List<String> steps) {
        StringBuilder formattedComment = new StringBuilder();

        for (String step : steps) {
            boolean containsAssert = step.toLowerCase().contains("assert");
            String formattedStep = step.replace("passed", "<span style='color: green;'>passed</span>");

            if (containsAssert) {
                formattedStep = "<strong>" + formattedStep + "</strong>";
            }

            formattedComment.append(formattedStep).append("<br>");
        }

        return formattedComment.toString();
    }

}
