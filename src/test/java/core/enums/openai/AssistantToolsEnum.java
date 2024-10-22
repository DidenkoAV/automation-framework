package core.enums.openai;

import lombok.Getter;

@Getter
public enum AssistantToolsEnum {
    RETRIEVAL("retrieval"),
    CODE_INTERPRETER("code_interpreter");

    AssistantToolsEnum(String tool) {
        this.tools = tool;
    }

    final String tools;

}
