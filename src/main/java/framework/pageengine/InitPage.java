package framework.pageengine;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitPage {
    public static final Logger logger = LoggerFactory.getLogger(InitPage.class);

    protected WebDriver driver;
    private String      windowHandle;

    public InitPage(WebDriver driver)
    {
        this.driver = driver;
        this.windowHandle = driver.getWindowHandle();
        this.initiateElements();
    }

    protected void initiateElements() {
        PageInitializer.initElements(driver, this);
    }
}
