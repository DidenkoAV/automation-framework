package framework.listener.testrail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.enums.testrail.TestRailCaseStatusEnum;
import framework.helpers.testrail.TestRailHelper;
import framework.helpers.allure.AllureHelper;
import io.qameta.allure.AllureId;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static framework.helpers.allure.AllureHelper.getAllureLogs;
import static framework.helpers.testng.TestNgHelper.getTestRailTestId;

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

        String logsString = String.join("\n", logs);
        TestRailHelper.setCaseStatusAndCommentByRunIdAndCaseId(runId, testRailId, statusEnum, logsString);
    }
}
