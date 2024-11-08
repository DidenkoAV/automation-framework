package framework.runners;

import framework.listeners.TestRailListener;
import org.testng.ITestResult;
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
        XmlPackage smokePackage = new XmlPackage("messagepoint.ui.smoke");
        packages.add(smokePackage);
        regressionTest.setPackages(packages);

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        testng.setXmlSuites(suites);

        testng.run();

        List<ITestResult> results = TestRailListener.getTestResults();
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
            List<String> logs = TestRailListener.getLogsForTelegram();
            output.append("Logs:\n").append(logs).append("\n");
        }

        return output.toString();
    }

    private static int countResults(List<ITestResult> results, int status) {
        int count = 0;
        for (ITestResult result : results) {
            if (result.getStatus() == status) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        runSmoke();
    }
}
