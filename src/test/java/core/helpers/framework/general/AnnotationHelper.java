package core.helpers.framework.general;

import core.annotations.TestParams;

import java.lang.reflect.Method;

public class AnnotationHelper {

    public static String parseBrowserFromTestParams(Method method){
        TestParams testParams = method.getAnnotation(TestParams.class);
        String browser = testParams.browser();
        return browser;
    }

    public static int getScenarioFromTestParams(Method method) {
        TestParams testParams = method.getAnnotation(TestParams.class);
        return testParams != null ? Integer.valueOf(testParams.scenario()) : -1;
    }

    public static int getRunIdFromTestParams(Method method) {
        TestParams testParams = method.getAnnotation(TestParams.class);
        return testParams != null ? testParams.runId() : -1;
    }
}
