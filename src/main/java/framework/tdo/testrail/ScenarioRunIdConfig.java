package framework.tdo.testrail;

public class ScenarioRunIdConfig {
    private final int scenario;
    private final int runId;

    public ScenarioRunIdConfig(int scenario, int runId) {
        this.scenario = scenario;
        this.runId = runId;
    }

    public int getScenario() {
        return scenario;
    }

    public int getRunId() {
        return runId;
    }
}
