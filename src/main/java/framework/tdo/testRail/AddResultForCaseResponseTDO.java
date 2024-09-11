package framework.tdo.testRail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class AddResultForCaseResponseTDO {
    @JsonProperty("attachment_ids")
    ArrayList<Object> attachmentIds;
    @JsonProperty("custom_testrail_bdd_scenario_results")
    Object custom_testrail_bdd_scenario_results;
    @JsonProperty("version")
    Object version;
    @JsonProperty("created_by")
    int createdBy;
    @JsonProperty("custom_step_results")
    Object customStepResults;
    @JsonProperty("elapsed")
    Object elapsed;
    @JsonProperty("status_id")
    int statusId;
    @JsonProperty("created_on")
    int createdOn;
    @JsonProperty("defects")
    Object defects;
    @JsonProperty("assignedto_id")
    Object assignedtoId;
    @JsonProperty("comment")
    Object comment;
    @JsonProperty("id")
    int id;
    @JsonProperty("test_id")
    int testId;
}
