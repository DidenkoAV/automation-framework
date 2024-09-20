package core.helpers.framework.testng;

import core.annotations.TestParams;
import io.qameta.allure.AllureId;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class TestNgHelper {
    //todo rename this method and helper
    public static String getTestRailTestId(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        String testId = null;
        if (method.isAnnotationPresent(TestParams.class)) {
            TestParams testRailId = method.getAnnotation(TestParams.class);
            testId = testRailId.testId();
            if (testId.isEmpty()) {
                throw new RuntimeException("AllureId annotation has been set but its value is empty.");
            }
        }
        return testId != null ? testId.replaceAll("\\D+", "") : "";
    }
}
