package framework.helpers.testng;

import framework.annotations.TestParams;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static framework.constants.GeneralConstants.ALL;
import static framework.constants.GeneralConstants.CSV_DATA;

public class DataProviderHelper {


    @DataProvider(name = CSV_DATA)
    public Object[][] csvDataProvider(Method method, ITestContext context) {
        TestParams testParams = method.getAnnotation(TestParams.class);

        boolean runningViaXml = isRunningViaXml(context);

        String csvPath = testParams.csvPath();
        String scenario;

        if (runningViaXml) {
            scenario = context.getCurrentXmlTest().getParameter("scenario");
            if(scenario==null){
              scenario = ALL;
            }
        } else {
            scenario = String.valueOf(testParams.scenario());
        }

        return parseCsvToMap(csvPath, method.getDeclaringClass(), scenario);
    }

    public static boolean isRunningViaXml(ITestContext context) {
        return context.getCurrentXmlTest().getParameter("runmode") != null;
    }

    public static Object[][] parseCsvToMap(String relativeFilePath, Class<?> clazz, String scenario) {
        List<HashMap<String, String>> dataList = new ArrayList<>();
        String line;

        String packagePath = clazz.getPackage().getName().replace('.', '/');
        String fullPath = packagePath + "/data/" + relativeFilePath;

        String filePath = System.getProperty("user.dir") + "/src/test/java/" + fullPath;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            String[] headers = headerLine.split(",");

            int lineCount = 0;
            if (ALL.equalsIgnoreCase(scenario)) {
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    String[] values = line.split(",");
                    HashMap<String, String> dataMap = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        dataMap.put(headers[i].trim(), values[i].trim());
                    }
                    dataList.add(dataMap);
                }
            } else {
                int scenarioLine = Integer.parseInt(scenario);
                while ((line = br.readLine()) != null) {
                    lineCount++;
                    if (lineCount == scenarioLine) {
                        String[] values = line.split(",");
                        HashMap<String, String> dataMap = new HashMap<>();
                        for (int i = 0; i < headers.length; i++) {
                            dataMap.put(headers[i].trim(), values[i].trim());
                        }
                        dataList.add(dataMap);
                        break;
                    }
                }
            }

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
