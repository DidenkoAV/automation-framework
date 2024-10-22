package core.tdo.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PromptResponse {
    @JsonProperty("id")
    String id;
    @JsonProperty("object")
    String object;
    @JsonProperty("created")
    int created;
    @JsonProperty("model")
    String model;
    @JsonProperty("choices")
    ArrayList<Choice> choices;
    @JsonProperty("usage")
    Usage usage;
    @JsonProperty("system_fingerprint")
    Object system_fingerprint;

    @Setter
    @Getter
    public static class Choice{
        @JsonProperty("index")
        int index;
        @JsonProperty("message")
        Message message;
        @JsonProperty("logprobs")
        Object logprobs;
        @JsonProperty("finish_reason")
        String finish_reason;
    }

    public static class CompletionTokensDetails{
        @JsonProperty("reasoning_tokens")
        int reasoning_tokens;
    }

    @Setter
    @Getter
    public static class Message{
        @JsonProperty("role")
        String role;
        @JsonProperty("content")
        String content;
        @JsonProperty("refusal")
        Object refusal;
    }

    @Setter
    public static class PromptTokensDetails{
        @JsonProperty("cached_tokens")
        int cached_tokens;
    }

    @Setter
    @Getter
    public static class Usage{
        @JsonProperty("prompt_tokens")
        int prompt_tokens;
        @JsonProperty("completion_tokens")
        int completion_tokens;
        @JsonProperty("total_tokens")
        int total_tokens;
        @JsonProperty("prompt_tokens_details")
        PromptTokensDetails prompt_tokens_details;
        @JsonProperty("completion_tokens_details")
        CompletionTokensDetails completion_tokens_details;
    }
}
