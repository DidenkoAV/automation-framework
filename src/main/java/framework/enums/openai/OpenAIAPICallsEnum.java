package framework.enums.openai;


import lombok.Getter;

@Getter
public enum OpenAIAPICallsEnum {
    CHAT_COMPLETIONS("chat/completions"),
    COMPLETIONS("completions"),
    AUDIO_SPEECH("audio/speech"),
    AUDIO_TRANSCRIPTIONS("audio/transcriptions"),
    AUDIO_TRANSLATIONS("audio/translations"),
    CREATE_IMAGE("images/generations"),
    VARIATE_IMAGE("images/variations"),
    MODERATIONS("moderations"),
    MODELS("models"),
    FILES("files"),
    FILE_TUNING("fine_tuning"),
    JOBS("jobs"),
    CONTENT("content"),
    ASSISTANTS("assistants"),
    THREADS("threads"),
    MESSAGES("messages"),
    EMBEDDINGS("embeddings"),
    RUNS("runs"),
    STEPS("steps"),
    OPEN_AI_BETA_VALUE("assistants=v1");

    OpenAIAPICallsEnum(String url) {
        this.url = url;
    }

    final String url;


}
