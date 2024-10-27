package framework.enums.general;

import lombok.Getter;

@Getter
public enum HttpEnum {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    final String http;

    HttpEnum(String http) {
        this.http = http;
    }
}
