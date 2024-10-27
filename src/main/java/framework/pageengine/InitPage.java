package framework.pageengine;

import org.openqa.selenium.WebDriver;

public class InitPage {
    protected WebDriver driver;

    public InitPage(WebDriver driver)
    {
        this.driver = driver;
        this.initiateElements();
    }

    protected void initiateElements() {
        PageInitializer.initElements(driver, this);
    }
}
