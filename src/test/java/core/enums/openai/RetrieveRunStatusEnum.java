package core.enums.openai;

import lombok.Getter;

@Getter
public enum RetrieveRunStatusEnum {
    COMPLETED("completed"),
    STATE("state"),
    QUEUED("queued");

    RetrieveRunStatusEnum(String queued) {
    }

    private String status;

}
