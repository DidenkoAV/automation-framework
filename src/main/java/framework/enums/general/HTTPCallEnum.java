package framework.enums.general;

import lombok.Getter;

@Getter
public enum HTTPCallEnum {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    final String http;

    HTTPCallEnum(String http) {
        this.http = http;
    }
}
