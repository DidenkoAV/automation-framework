package framework.tdo.testrail;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestConfig {
    private String browser;
    private String runId;
    private String scenario;

    public TestConfig(String browser, String runId, String scenario) {
        this.browser = browser;
        this.runId = runId;
        this.scenario = scenario;
    }

}
