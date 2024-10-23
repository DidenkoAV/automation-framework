package core.helpers.framework.testng;

import core.listeners.TestRailListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

public class TestNgRunner {

    public static String runSmoke() {
        TestNG testng = new TestNG();
        XmlSuite suite = new XmlSuite();
        suite.setName("Smoke test Suite");

        XmlTest regressionTest = new XmlTest(suite);
        regressionTest.setName("Regression");

        regressionTest.addParameter("runmode", "xml");
        regressionTest.addParameter("browser", "chrome");
        regressionTest.addParameter("runId", "1");

        List<XmlPackage> packages = new ArrayList<>();
        XmlPackage smoke = new XmlPackage("tests.umh.smoke.*");
        packages.add(smoke);
        regressionTest.setPackages(packages);

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        testng.setXmlSuites(suites);

        // Create instances of listeners
        TestRailListener testRailListener = new TestRailListener();

        // Add listeners to TestNG
        testng.addListener(testRailListener);

        testng.run();

        List<ITestResult> results = testRailListener.getTestResults();
        StringBuilder output = new StringBuilder();

        output.append("===============================================\n");
        output.append("Smoke test Suite\n");
        output.append("Total tests run: ").append(results.size()).append("\n");
        output.append("Passes: ").append(countResults(results, ITestResult.SUCCESS)).append(", ");
        output.append("Failures: ").append(countResults(results, ITestResult.FAILURE)).append(", ");
        output.append("Skips: ").append(countResults(results, ITestResult.SKIP)).append("\n");
        output.append("===============================================\n");

        for (ITestResult result : results) {
            output.append("Test: ").append(result.getName()).append("\n");
            output.append("Status: ").append(result.getStatus() == ITestResult.SUCCESS ? "Passed" : "Failed").append("\n");
            if (result.getThrowable() != null) {
                output.append("Error: ").append(result.getThrowable().getMessage()).append("\n");
            }
            output.append("Logs:\n").append(getLogsForTest(result)).append("\n");
        }

        return output.toString();
    }

    private static int countResults(List<ITestResult> results, int status) {
        return (int) results.stream().filter(result -> result.getStatus() == status).count();
    }

    private static String getLogsForTest(ITestResult result) {
        return "Detailed log messages for " + result.getName();
    }

    public static void main(String[] args) {
        String string = TestNgRunner.runSmoke();
    }
}
