package helpers;

import io.qameta.allure.AllureId;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class TestNgHelper {
    public static String getTestRailTestId(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        String testId = null;
        if (method.isAnnotationPresent(AllureId.class)) {
            AllureId allureIdAnnotation = method.getAnnotation(AllureId.class);
            testId = allureIdAnnotation.value();
            if (testId.isEmpty()) {
                throw new RuntimeException("AllureId annotation has been set but its value is empty.");
            }
        }
        return testId != null ? testId.replaceAll("\\D+", "") : "";
    }
}
