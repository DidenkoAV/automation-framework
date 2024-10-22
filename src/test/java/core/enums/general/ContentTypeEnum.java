package core.enums.general;

import lombok.Getter;

@Getter
public enum ContentTypeEnum {
    JSON("application/json"),
    OCTET_STREAM("application/octet-stream"),
    TEXT("text/plain");

    private final String contentType;

    ContentTypeEnum(String contentType) {
        this.contentType = contentType;
    }

}
