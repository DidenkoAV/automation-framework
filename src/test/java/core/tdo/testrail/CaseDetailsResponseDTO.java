package core.tdo.testrail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CaseDetailsResponseDTO {
    @JsonProperty("id")
    int id;
    @JsonProperty("updated_on")
    int updatedOn;
    @JsonProperty("custom_expected")
    Object customExpected;
    @JsonProperty("custom_steps_separated")
    Object customStepsSeparated;
    @JsonProperty("milestone_id")
    Object milestoneId;
    @JsonProperty("display_order")
    int displayOrder;
    @JsonProperty("title")
    String title;
    @JsonProperty("custom_steps")
    Object customSteps;
    @JsonProperty("priority_id")
    int priorityId;
    @JsonProperty("is_deleted")
    int isDeleted;
    @JsonProperty("custom_testrail_bdd_scenario")
    Object customTestrailBddScenario;
    @JsonProperty("section_id")
    int sectionId;
    @JsonProperty("case_assignedto_id")
    Object caseAssigneDtoId;
    @JsonProperty("estimate")
    Object estimate;
    @JsonProperty("suite_id")
    int suiteId;
    @JsonProperty("comments")
    ArrayList<Object> comments;
    @JsonProperty("custom_mission")
    Object customMission;
    @JsonProperty("estimate_forecast")
    Object estimateForecast;
    @JsonProperty("custom_preconds")
    Object customPreconds;
    @JsonProperty("custom_goals")
    Object customGoals;
    @JsonProperty("created_by")
    int createdBy;
    @JsonProperty("type_id")
    int typeId;
    @JsonProperty("refs")
    Object refs;
    @JsonProperty("created_on")
    int createdOn;
    @JsonProperty("case_status_id")
    int caseStatusId;
    @JsonProperty("custom_automation_type")
    int customAutomationType;
    @JsonProperty("updated_by")
    int updatedBy;
    @JsonProperty("template_id")
    int templateId;

}
