package framework.listeners;

import framework.enums.testrail.TestRailCaseStatusEnum;
import framework.helpers.allure.AllureHelper;
import framework.helpers.general.AnnotationHelper;
import framework.helpers.general.PropertiesReaderHelper;
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
import static framework.helpers.testng.DataProviderHelper.isRunningViaXml;

public class TestRailListener implements ITestListener {
    @Getter
    private static final List<ITestResult> testResults = new ArrayList<>();
    @Getter
    public static List<String> logsForTelegram = new ArrayList<>();
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
        ScenarioRunIdConfig scenarioRunIdConfig = defineScenarioAndRunId(context,method);
        boolean displaySubStep = displaySubStep();

        String logsString = collectSl4jLogs(displaySubStep);
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

    public static String collectSl4jLogs(boolean displaySubstep){
        AssertionHelper.getStatistics();
        AssertionHelper.assertAll();
        List<String> logs = LogHelper.getLogs();
        return LogHelper.formatSL4JLogsForTestRail(logs,displaySubstep);
    }

    public static boolean displaySubStep(){
        PropertiesReaderHelper helper = new PropertiesReaderHelper(INIT_PROPERTIES);
        return Boolean.parseBoolean(helper.getProperty("testrail.display.substep").trim());
    }

    public ScenarioRunIdConfig defineScenarioAndRunId(ITestContext context, Method method) {
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

        return new ScenarioRunIdConfig(scenario, runId);
    }

}
