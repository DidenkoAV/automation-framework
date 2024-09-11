package framework.enums.testRail;

import java.util.HashMap;
import java.util.Map;

public enum TestRailCaseStatusEnum {
    PASSED(1),
    BLOCKED(2),
    UNTESTED(3),
    RETEST(4),
    FAILED(5);

    private final int status;
    private static final Map<Integer, TestRailCaseStatusEnum> lookup = new HashMap<>();

    static {
        for (TestRailCaseStatusEnum statusEnum : TestRailCaseStatusEnum.values()) {
            lookup.put(statusEnum.status, statusEnum);
        }
    }

    TestRailCaseStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static TestRailCaseStatusEnum get(String status) {
        TestRailCaseStatusEnum statusEnum = lookup.get(status);
        if (statusEnum != null) {
            return statusEnum;
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}
