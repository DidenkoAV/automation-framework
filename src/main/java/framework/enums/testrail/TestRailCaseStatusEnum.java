package framework.enums.testrail;

import java.util.Arrays;

public enum TestRailCaseStatusEnum {
    PASSED(1),
    BLOCKED(2),
    UNTESTED(3),
    RETEST(4),
    FAILED(5);

    private final int status;

    TestRailCaseStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static TestRailCaseStatusEnum get(int status) {
        return Arrays.stream(values())
                .filter(statusEnum -> statusEnum.status == status)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + status));
    }
}
