package framework.helpers.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class LoadBrowser {
    protected static WebDriver driver;


    public static WebDriver loadChrome(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = initChromeOptions();
        driver = new ChromeDriver(options);
        return driver;
    }

    public static WebDriver loadFirefox(){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = initFirefoxOption();
        driver = new FirefoxDriver(options);
        return driver;
    }

    private static ChromeOptions initChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    private static  FirefoxOptions initFirefoxOption(){
        FirefoxOptions options = new FirefoxOptions();
        return options;
    }
}
