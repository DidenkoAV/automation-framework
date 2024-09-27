package core.listeners;

import core.enums.testrail.TestRailCaseStatusEnum;
import core.helpers.framework.allure.AllureHelper;
import core.helpers.framework.general.AnnotationHelper;
import core.helpers.framework.general.PropertiesReaderHelper;
import core.helpers.framework.log.LogHelper;
import core.helpers.framework.testrail.TestRailHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.util.List;

import static core.helpers.framework.testng.DataProviderHelper.isRunningViaXml;

public class TestRailListener implements ITestListener {
    public static int ALL_SCENARIOS = 1;

    @Override
    public void onStart(ITestContext context) {
        AllureHelper.clearAllureFolder();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        handleTestResult(result, TestRailCaseStatusEnum.PASSED, result.getTestContext());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        handleTestResult(result, TestRailCaseStatusEnum.FAILED, result.getTestContext());
    }

//    @Override
//    public void onTestSkipped(ITestResult result) {
//        handleTestResult(result, TestRailCaseStatusEnum.SKIPPED, result.getTestContext());
//    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    private void handleTestResult(ITestResult result, TestRailCaseStatusEnum statusEnum, ITestContext context) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        String classAndMethodPath = getClassAndMethodPath(method);
        int scenario;
        int runId;

        if (isRunningViaXml(context)) {
            String scenarioParam = context.getCurrentXmlTest().getParameter("scenario");
            String runIdParam = context.getCurrentXmlTest().getParameter("runId");

            if(scenarioParam==null){
                scenarioParam = String.valueOf(ALL_SCENARIOS);
            }

            scenario = (scenarioParam != null) ? Integer.parseInt(scenarioParam) : -1;
            runId = (runIdParam != null) ? Integer.parseInt(runIdParam) : -1;
        } else {
            scenario = AnnotationHelper.getScenarioFromTestParams(method);
            runId = AnnotationHelper.getRunIdFromTestParams(method);
        }
        PropertiesReaderHelper helper = new PropertiesReaderHelper("init.properties");
        boolean displaySubstep = Boolean.valueOf(helper.getProperty("testrail.display.substep").trim());

        List<String> logs = LogHelper.getLogs();
        String logsString = LogHelper.formatSL4JLogsForTestRail(logs,displaySubstep);

        TestRailHelper.setCaseStatusAndCommentByScenarioAndMethod(runId, classAndMethodPath, scenario, statusEnum, logsString);
        ALL_SCENARIOS++;
        LogHelper.clearLogs();
    }

    private static String getClassAndMethodPath(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        String methodName = method.getName();
        return declaringClass.getName() + "." + methodName;
    }
}
