package framework.tdo.testrail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class TestListByRunIdResponse {
    @JsonProperty("custom_expected")
    Object custom_expected;
    @JsonProperty("custom_steps_separated")
    Object custom_steps_separated;
    @JsonProperty("milestone_id")
    Object milestone_id;
    @JsonProperty("title")
    String title;
    @JsonProperty("case_comments")
    ArrayList<Object> case_comments;
    @JsonProperty("custom_steps")
    Object custom_steps;
    @JsonProperty("priority_id")
    int priority_id;
    @JsonProperty("status_id")
    int status_id;
    @JsonProperty("custom_testrail_bdd_scenario")
    Object custom_testrail_bdd_scenario;
    @JsonProperty("custom_test_method")
    String custom_test_method;
    @JsonProperty("case_assignedto_id")
    Object case_assignedto_id;
    @JsonProperty("case_id")
    int case_id;
    @JsonProperty("estimate")
    Object estimate;
    @JsonProperty("sections_display_order")
    int sections_display_order;
    @JsonProperty("id")
    int id;
    @JsonProperty("custom_mission")
    Object custom_mission;
    @JsonProperty("run_id")
    int run_id;
    @JsonProperty("type_id")
    int type_id;
    @JsonProperty("estimate_forecast")
    Object estimate_forecast;
    @JsonProperty("cases_display_order")
    int cases_display_order;
    @JsonProperty("custom_preconds")
    Object custom_preconds;
    @JsonProperty("custom_goals")
    Object custom_goals;
    @JsonProperty("refs")
    Object refs;
    @JsonProperty("custom_scenario")
    String custom_scenario;
    @JsonProperty("case_status_id")
    int case_status_id;
    @JsonProperty("assignedto_id")
    Object assignedto_id;
    @JsonProperty("template_id")
    int template_id;
    @JsonProperty("custom_automation_type")
    int custom_automation_type;
}
