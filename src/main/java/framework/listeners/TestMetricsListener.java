package framework.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.concurrent.atomic.AtomicInteger;

public class TestMetricsListener implements ITestListener, ISuiteListener {

    private final AtomicInteger totalTests = new AtomicInteger(0);
    private final AtomicInteger passedTests = new AtomicInteger(0);
    private final AtomicInteger failedTests = new AtomicInteger(0);
    private final AtomicInteger skippedTests = new AtomicInteger(0);

    @Override
    public void onStart(ISuite suite) {
        totalTests.set(0);
        passedTests.set(0);
        failedTests.set(0);
        skippedTests.set(0);
    }

    @Override
    public void onFinish(ISuite suite) {
        int total = totalTests.get();
        int passed = passedTests.get();
        int failed = failedTests.get();
        int skipped = skippedTests.get();

        double failurePercentage = total > 0 ? ((double) failed / total) * 100 : 0.0;

        System.out.println("Test Suite Summary:");
        System.out.println("Total tests executed: " + total);
        System.out.println("Passed tests: " + passed);
        System.out.println("Failed tests: " + failed);
        System.out.println("Skipped tests: " + skipped);
        System.out.println("Failure percentage: " + String.format("%.2f", failurePercentage) + "%");
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    @Override
    public void onTestStart(ITestResult result) {
        totalTests.incrementAndGet();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests.incrementAndGet();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedTests.incrementAndGet();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests.incrementAndGet();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
}
