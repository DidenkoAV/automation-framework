package core.enums.selenium;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum BrowserEnum {
    CHROME("chrome"),
    FIREFOX("firefox");

    private final String browser;
    private static final Map<String, BrowserEnum> lookup = new HashMap<>();

    static {
        for (BrowserEnum browserEnum : BrowserEnum.values()) {
            lookup.put(browserEnum.browser, browserEnum);
        }
    }

    BrowserEnum(String browser) {
        this.browser = browser;
    }


    public static BrowserEnum get(String browser) {
        BrowserEnum browserEnum = lookup.get(browser);
        if (browserEnum != null) {
            return browserEnum;
        }
        throw new RuntimeException("Invalid Browser: " + browser);
    }
}
