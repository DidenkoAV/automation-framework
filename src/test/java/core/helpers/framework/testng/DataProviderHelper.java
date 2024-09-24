package core.helpers.framework.testng;

import core.annotations.TestParams;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataProviderHelper {

    @DataProvider(name = "csvData")
    public Object[][] csvDataProvider(Method method) {
        TestParams csvData = method.getAnnotation(TestParams.class);
        if (csvData != null) {
            return parseCsvToMap(csvData.csvPath(), method.getDeclaringClass(), csvData.scenario());
        }
        return new Object[0][0];
    }

    public static Object[][] parseCsvToMap(String relativeFilePath, Class<?> clazz, int scenario) {
        List<HashMap<String, String>> dataList = new ArrayList<>();
        String line;

        // Get the path to the directory of the class
        String packagePath = clazz.getPackage().getName().replace('.', '/');
        String fullPath = packagePath + "/data/" + relativeFilePath;

        // Construct the full file path based on the test directory
        String filePath = System.getProperty("user.dir") + "/src/test/java/" + fullPath;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            String[] headers = headerLine.split(",");

            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount == scenario) {
                    String[] values = line.split(",");
                    HashMap<String, String> dataMap = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        dataMap.put(headers[i].trim(), values[i].trim());
                    }
                    dataList.add(dataMap);
                    break; // Exit after finding the scenario
                }
            }

            // Convert List<HashMap<String, String>> to Object[][]
            Object[][] result = new Object[dataList.size()][1];
            for (int i = 0; i < dataList.size(); i++) {
                result[i][0] = dataList.get(i);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}
