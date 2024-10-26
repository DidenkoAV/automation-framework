package framework.helpers.log;

import framework.helpers.general.PropertiesReaderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LogHelper {
    private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);
    private static final List<String> logs = new ArrayList<>();

    public static void step(String message) {
        logger.info(message);
        logs.add("[STEP] " + message);
    }

    public static void substep(String message) {
        logger.info(message);
        logs.add("[SUBSTEP] " + message);
    }
    public static void clearLogs() {
        logs.clear();
    }

    public static List<String> getLogs() {
        return logs;
    }
    public static void assertStep(String message){
        logger.info(message);
        logs.add("[ASSERT] " + message);
    }

    public static void verifyStep(String message){
        logger.info(message);
        logs.add("Verify:" + message);
    }

    public static String formatSL4JLogsForTestRail(List<String> logs, boolean showSubsteps) {
        String color = new PropertiesReaderHelper("init.properties").getProperty("testrail.logs.color");
        String assertColor = new PropertiesReaderHelper("init.properties").getProperty("testrail.logs.assert.color");
        int stepsCount = 1;
        StringBuilder formattedLogs = new StringBuilder("<div style='font-family: Arial, sans-serif; color: #333;'>");

        for (String log : logs) {
            String cleanLog = log.replace("[STEP]", "").replace("[SUBSTEP]", "").replace("[ASSERT]", "").trim();

            if (log.contains("[STEP]")) {
                formattedLogs.append("<span style='font-size: 12px; color: " + color + ";'>")
                        .append(stepsCount).append(". ")
                        .append(cleanLog).append("</span><br/>");
                stepsCount++;
            } else if (log.contains("[SUBSTEP]") && showSubsteps) {
                formattedLogs.append("<span style='font-size: 12px; color: " + color + "; margin-left: 20px;'>• ")
                        .append(cleanLog).append("</span><br/>");
            } else if (log.contains("[ASSERT]")) {
                formattedLogs.append("<span style='font-size: 12px; color:"  + assertColor + ";'>")
                        .append("Assertion: ").append(cleanLog).append("</span><br/>");
            }else if (log.contains("Verify")) {
                formattedLogs.append("<span style='font-size: 12px; color:"  + assertColor + ";'>")
                        .append(cleanLog).append("</span><br/>");
            }
        }

        formattedLogs.append("</div>");
        return formattedLogs.toString();
    }



    public static String formatAllureLogsForTestRail(List<String> steps) {
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
