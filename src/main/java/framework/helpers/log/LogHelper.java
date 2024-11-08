package framework.helpers.log;

import framework.helpers.general.PropertyHelper;
import framework.helpers.testng.AssertionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static framework.constants.GeneralConstants.*;

public class LogHelper {
    private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);
    private static final List<String> logs = new ArrayList<>();
    private static final String STEP = "[STEP]";
    private static final String SUBSTEP = "[SUBSTEP]";
    private static final String ASSERT = "[ASSERT]";
    private static final String VERIFY = "[VERIFY]";

    public static void step(String message) {
        logger.info(message);
        logs.add(STEP + message);
    }

    public static void warn(String message){
        logger.debug(message);
    }

    public static void substep(String message) {
        logger.info(message);
        logs.add(SUBSTEP + message);
    }

    public static void clearLogs() {
        logs.clear();
    }

    public static List<String> getLogs() {
        return logs;
    }

    public static void assertStep(String message) {
        logger.info(message);
        logs.add(ASSERT + message);
    }

    public static void verifyStep(String message) {
        logger.info(message);
        logs.add(VERIFY + message);
    }

    public static String formatSL4JLogsForTestRail(List<String> logs, boolean showSubsteps) {
        String color = PropertyHelper.initAndGetProperty(INIT_PROPERTIES,TESTRAIL_LOGS_COLOR);
        String assertColor = PropertyHelper.initAndGetProperty(INIT_PROPERTIES,TESTRAIL_LOGS_ASSERT_COLOR);

        int stepsCount = 1;
        StringBuilder formattedLogs = new StringBuilder("<div style='font-family: Arial, sans-serif; color: #333;'>");

        for (String log : logs) {
            String cleanLog = log.replace(STEP, "").replace("[SUBSTEP]", "").replace("[ASSERT]", "").trim();

            if (log.contains(STEP)) {
                formattedLogs.append("<span style='font-size: 12px; color: " + color + ";'>").append(stepsCount).append(". ").append(cleanLog).append("</span><br/>");
                stepsCount++;
            } else if (log.contains(SUBSTEP) && showSubsteps) {
                formattedLogs.append("<span style='font-size: 12px; color: " + color + "; margin-left: 20px;'>• ").append(cleanLog).append("</span><br/>");
            } else if (log.contains(ASSERT)) {
                formattedLogs.append("<span style='font-size: 12px; color:" + assertColor + ";'>").append("Assertion: ").append(cleanLog).append("</span><br/>");
            } else if (log.contains(VERIFY)) {
                formattedLogs.append("<span style='font-size: 12px; color:" + assertColor + ";'>").append(cleanLog).append("</span><br/>");
            }
        }

        formattedLogs.append("</div>");
        return formattedLogs.toString();
    }

    public static String collectSl4jLogsForTestRail(boolean displaySubstep){
        AssertionHelper.getStatistics();
        AssertionHelper.assertAll();
        List<String> logs = LogHelper.getLogs();
        return LogHelper.formatSL4JLogsForTestRail(logs,displaySubstep);
    }
}
