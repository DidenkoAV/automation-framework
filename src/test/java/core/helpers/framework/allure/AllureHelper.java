package core.helpers.framework.allure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.helpers.framework.general.FileHelper;
import core.helpers.framework.general.PropertiesReaderHelper;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AllureHelper {


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

        PropertiesReaderHelper propertiesReaderHelper = new PropertiesReaderHelper("allure.properties");
        String allurePath = propertiesReaderHelper.getProperty("allure.results.directory");

        File resultsDir = new File(allurePath);
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

    public static void clearAllureFolder(){
        PropertiesReaderHelper helper = new PropertiesReaderHelper("allure.properties");
        String allureDir = helper.getProperty("allure.results.directory");
        FileHelper.deleteDirectory(new File(allureDir));
    }
}
