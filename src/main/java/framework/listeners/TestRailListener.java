package framework.listeners;

import framework.enums.testrail.TestRailCaseStatusEnum;
import framework.helpers.allure.AllureHelper;
import framework.helpers.general.AnnotationHelper;
import framework.helpers.general.PropertyHelper;
import framework.helpers.log.LogHelper;
import framework.helpers.testng.AssertionHelper;
import framework.helpers.testrail.TestRailHelper;
import framework.tdo.testrail.ScenarioRunIdConfig;
import lombok.Getter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static framework.constants.GeneralConstants.INIT_PROPERTIES;
import static framework.constants.GeneralConstants.TESTRAIL_DISPLAY_SUBSTEP;
import static framework.constants.testrail.TestRailConstants.RUNID;
import static framework.constants.testrail.TestRailConstants.SCENARIO;
import static framework.helpers.testng.DataProviderHelper.isRunningViaXml;

public class TestRailListener implements ITestListener {
    @Getter
    private static final List<ITestResult> testResults = new ArrayList<>();
    @Getter
    public static List<String> logsForTelegram = new ArrayList<>(); //bad approach
    public static int ALL_SCENARIOS = 1;

    @Override
    public void onStart(ITestContext context) {
        AllureHelper.clearAllureFolder();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        handleTestResult(result, TestRailCaseStatusEnum.PASSED, result.getTestContext());
        testResults.add(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        handleTestResult(result, TestRailCaseStatusEnum.FAILED, result.getTestContext());
        testResults.add(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        testResults.add(result);
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    private void handleTestResult(ITestResult result, TestRailCaseStatusEnum statusEnum, ITestContext context) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        String classAndMethodPath = getClassAndMethodPath(method);
        ScenarioRunIdConfig scenarioRunIdConfig = TestRailHelper.defineScenarioAndRunId(context,method);
        boolean displaySubStep = displaySubStep();

        String logsString = LogHelper.collectSl4jLogsForTestRail(displaySubStep);
        TestRailHelper.setCaseStatusAndCommentByScenarioAndMethod(scenarioRunIdConfig.getRunId(), classAndMethodPath, scenarioRunIdConfig.getScenario(), statusEnum, logsString);
        ALL_SCENARIOS++;
        logsForTelegram = new ArrayList<>(LogHelper.getLogs());
        LogHelper.clearLogs();
    }

    private static String getClassAndMethodPath(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        String methodName = method.getName();
        return declaringClass.getName() + "." + methodName;
    }

    public static boolean displaySubStep(){
        return Boolean.parseBoolean(PropertyHelper.initAndGetProperty(INIT_PROPERTIES, TESTRAIL_DISPLAY_SUBSTEP));
    }

}
