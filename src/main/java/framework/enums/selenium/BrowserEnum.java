package framework.enums.selenium;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum BrowserEnum {
    CHROME("chrome"),
    FIREFOX("firefox");

    private final String browser;

    BrowserEnum(String browser) {
        this.browser = browser;
    }

    public static BrowserEnum get(String browser) {
        return Arrays.stream(values())
                .filter(browserEnum -> browserEnum.browser.equalsIgnoreCase(browser))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid Browser: " + browser));
    }
}
