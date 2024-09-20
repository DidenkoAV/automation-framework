package core.listeners;

import core.annotations.TestParams;
import core.enums.testrail.TestRailCaseStatusEnum;
import core.helpers.framework.allure.AllureHelper;
import core.helpers.framework.general.FileHelper;
import core.helpers.framework.testrail.TestRailHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import static core.helpers.framework.allure.AllureHelper.ALLURE_RESULTS_DIR;


public class TestRailListener implements ITestListener {


    @Override
    public void onStart(ITestContext context) {
        FileHelper.deleteDirectory(new File(ALLURE_RESULTS_DIR));

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
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        String classAndMethodPath = getClassAndMethodPath(method);
        int scenario = getScenarioFromTestParamsAnnotation(method);
        int runId = getRunIdFromTestParamsAnnotation(method);

        List<String> logs = AllureHelper.getAllureLogs();
        String logsString = TestRailHelper.formatComments(logs);

        TestRailHelper.setCaseStatusAndCommentByScenarioAndMethod(runId, classAndMethodPath, scenario, statusEnum, logsString);
    }


    private static int getScenarioFromTestParamsAnnotation(Method method) {
        TestParams testParams = method.getAnnotation(TestParams.class);
        return testParams != null ? testParams.scenario() : -1;
    }

    private static int getRunIdFromTestParamsAnnotation(Method method) {
        TestParams testParams = method.getAnnotation(TestParams.class);
        return testParams != null ? testParams.runId() : -1;
    }

    private static String getClassAndMethodPath(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        String methodName = method.getName();
        return declaringClass.getName() + "." + methodName;
    }
}
