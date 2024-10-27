package framework.enums.openai;

import lombok.Getter;

@Getter
public enum PurposeValuesEnum {
    FINE_TUNE("fine-tune"),
    ASSISTANTS("assistants");

    PurposeValuesEnum(String modelName) {
        this.purposeValue = modelName;
    }

    final String purposeValue;
}
