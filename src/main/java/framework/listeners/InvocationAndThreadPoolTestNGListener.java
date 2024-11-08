package framework.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class InvocationAndThreadPoolTestNGListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        int invocationCount = Integer.parseInt(System.getProperty("invocationCount", "1"));
        int threadPoolSize = Integer.parseInt(System.getProperty("threadPoolSize", "1"));

        annotation.setInvocationCount(invocationCount);
        annotation.setThreadPoolSize(threadPoolSize);
    }
}
