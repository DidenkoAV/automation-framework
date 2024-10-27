package framework.tdo.testrail;

import lombok.Getter;

@Getter
public class ScenarioRunIdConfig {
    private final int scenario;
    private final int runId;

    public ScenarioRunIdConfig(int scenario, int runId) {
        this.scenario = scenario;
        this.runId = runId;
    }

}
