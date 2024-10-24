package core.tdo.testrail;

public class ScenarioRunId {
    private final int scenario;
    private final int runId;

    public ScenarioRunId(int scenario, int runId) {
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
