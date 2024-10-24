package core.listeners;

import core.enums.testrail.TestRailCaseStatusEnum;
import core.helpers.framework.allure.AllureHelper;
import core.helpers.framework.general.AnnotationHelper;
import core.helpers.framework.general.PropertiesReaderHelper;
import core.helpers.framework.log.LogHelper;
import core.helpers.framework.testng.AssertionHelper;
import core.helpers.framework.testrail.TestRailHelper;
import core.tdo.testrail.ScenarioRunId;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static core.helpers.framework.testng.DataProviderHelper.isRunningViaXml;

public class TestRailListener implements ITestListener {
    private final List<ITestResult> testResults = new ArrayList<>();
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

    public List<ITestResult> getTestResults() {
        return testResults;
    }

    private void handleTestResult(ITestResult result, TestRailCaseStatusEnum statusEnum, ITestContext context) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        String classAndMethodPath = getClassAndMethodPath(method);
        ScenarioRunId scenarioRunId = defineScenarioAndRunId(context,method);
        boolean displaySubStep = displaySubStep();

        String logsString = collectLogsForTestRail(displaySubStep);
        TestRailHelper.setCaseStatusAndCommentByScenarioAndMethod(scenarioRunId.getRunId(), classAndMethodPath, scenarioRunId.getScenario(), statusEnum, logsString);
        ALL_SCENARIOS++;
        LogHelper.clearLogs();
    }

    private static String getClassAndMethodPath(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        String methodName = method.getName();
        return declaringClass.getName() + "." + methodName;
    }

    private static String collectLogsForTestRail(boolean displaySubstep){
        AssertionHelper.getStatistics();
        AssertionHelper.assertAll();
        List<String> logs = LogHelper.getLogs();
        return LogHelper.formatSL4JLogsForTestRail(logs,displaySubstep);
    }

    private static boolean displaySubStep(){
        PropertiesReaderHelper helper = new PropertiesReaderHelper("init.properties");
        return Boolean.parseBoolean(helper.getProperty("testrail.display.substep").trim());
    }

    public ScenarioRunId defineScenarioAndRunId(ITestContext context, Method method) {
        int scenario;
        int runId;

        if (isRunningViaXml(context)) {
            String scenarioParam = context.getCurrentXmlTest().getParameter("scenario");
            String runIdParam = context.getCurrentXmlTest().getParameter("runId");

            if (scenarioParam == null) {
                scenarioParam = String.valueOf(ALL_SCENARIOS);
            }

            scenario = Integer.parseInt(scenarioParam);
            runId = (runIdParam != null) ? Integer.parseInt(runIdParam) : -1;
        } else {
            scenario = AnnotationHelper.getScenarioFromTestParams(method);
            runId = AnnotationHelper.getRunIdFromTestParams(method);
        }

        return new ScenarioRunId(scenario, runId);
    }

}
