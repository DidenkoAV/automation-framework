package core.helpers.framework.testng;

import core.annotations.TestData;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataProviderHelper {

    @DataProvider(name = "csvData")
    public Object[][] csvDataProvider(Method method)  {
        TestData csvData = method.getAnnotation(TestData.class);
        if (csvData != null) {
            return parseCsvToMap(csvData.path());
        }
        return new Object[0][0];
    }

    public static Object[][] parseCsvToMap(String filePath)  {
        List<HashMap<String, String>> dataList = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Read header line
            String[] headers = headerLine.split(","); // Adjust delimiter if necessary

            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Adjust for your CSV delimiter
                HashMap<String, String> dataMap = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    dataMap.put(headers[i].trim(), values[i].trim());
                }
                dataList.add(dataMap);
            }


            // Convert List<HashMap<String, String>> to Object[][]
            Object[][] result = new Object[dataList.size()][1];
            for (int i = 0; i < dataList.size(); i++) {
                result[i][0] = dataList.get(i);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
