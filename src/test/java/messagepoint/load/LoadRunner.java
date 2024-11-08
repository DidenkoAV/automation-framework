package messagepoint.load;

import framework.listeners.InvocationAndThreadPoolTestNGListener;
import framework.listeners.TestMetricsListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoadRunner {
    public static void main(String[] args) {
        try {
            String invocationCount = System.setProperty("invocationCount", "2");
            String threadPoolSize = System.setProperty("threadPoolSize", "2");

            XmlSuite suite = new XmlSuite();
            suite.setName("Smoke test Suite");

            XmlTest test = new XmlTest(suite);
            test.setName("Regression");

            Map<String, String> parameters = new HashMap<>();
            parameters.put("browser", "chrome");
            parameters.put("invocationCount", invocationCount);
            parameters.put("threadPoolSize", threadPoolSize);
            test.setParameters(parameters);

            XmlClass testClass = new XmlClass("messagepoint.load.LoginLoadTest");
            test.setXmlClasses(Collections.singletonList(testClass));

            TestNG testNG = new TestNG();
            testNG.setXmlSuites(Collections.singletonList(suite));

            testNG.addListener(new InvocationAndThreadPoolTestNGListener());
            testNG.addListener(new TestMetricsListener());

            testNG.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
