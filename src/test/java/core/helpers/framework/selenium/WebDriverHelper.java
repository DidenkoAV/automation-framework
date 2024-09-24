package core.helpers.framework.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebDriverHelper  {

    public static void enterTextByXpath(WebDriver driver, String text, String xpath){
        driver.findElement(By.xpath(xpath)).sendKeys(text);
    }
    public static void clickElement(WebDriver driver, String xpath){
        driver.findElement(By.xpath(xpath)).click();
    }


}
