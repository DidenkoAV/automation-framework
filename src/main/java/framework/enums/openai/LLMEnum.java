package framework.enums.openai;

import lombok.Getter;

@Getter
public enum LLMEnum {
    GPT35_TURBO("gpt-3.5-turbo"),
    GPT4("gpt-4"),
    TEXT_EMBEDDING_ADA_002("text-embedding-ada-002");

    LLMEnum(String modelName) {
        this.modelName = modelName;
    }

    final String modelName;

}
