package core.tdo.testrail;

public class TestConfig {
    private String browser;
    private String runId;
    private String scenario;

    public TestConfig(String browser, String runId, String scenario) {
        this.browser = browser;
        this.runId = runId;
        this.scenario = scenario;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
