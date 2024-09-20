package core.helpers.framework.allure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AllureHelper {
    public static final String ALLURE_RESULTS_DIR = "allure-results";


    public static void collectStepLogs(JsonNode stepsNode, List<String> logs, ObjectMapper mapper){
        for (JsonNode step : stepsNode) {
            String stepName = step.path("name").asText();
            String stepStatus = step.path("status").asText();
            logs.add(stepName + ": " + stepStatus);
            JsonNode nestedStepsNode = step.path("steps");
            collectStepLogs(nestedStepsNode, logs, mapper);
        }
    }


    public static List<String> getAllureLogs() {
        List<String> logs = new ArrayList<>();

        File resultsDir = new File(ALLURE_RESULTS_DIR);
        File[] resultFiles = resultsDir.listFiles((dir, name) -> name.endsWith("-result.json"));

        if (resultFiles == null) {
            return logs;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            for (File resultFile : resultFiles) {
                JsonNode resultNode = mapper.readTree(resultFile);
                JsonNode stepsNode = resultNode.path("steps");
                AllureHelper.collectStepLogs(stepsNode, logs, mapper);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logs;
    }
}
