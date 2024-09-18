package framework.pageengine;

import org.openqa.selenium.WebDriver;

public class InitPage {
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
