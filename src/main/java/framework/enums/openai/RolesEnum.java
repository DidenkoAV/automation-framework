package framework.enums.openai;

import lombok.Getter;

@Getter
public enum RolesEnum {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");
    RolesEnum(String roles) {
        this.role=roles;
    }

    final String role;

}
