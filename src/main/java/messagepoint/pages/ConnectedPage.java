package messagepoint.pages;

import framework.helpers.log.LogHelper;
import framework.helpers.selenium.WebDriverHelper;
import framework.pageengine.InitPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class ConnectedPage extends InitPage {
    public static final String ADD_ON_MODULES        = "//*[@class='fa-stack mt-n1 w-auto']";
    public static final String CONNECTED_MODULE_ITEM = "//*[@id='connectedMenuItem']";
    public static final String ADD_BUTTON            = "//*[@class='btn btn-primary']";

    public ConnectedPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click add on modules button")
    public void clickAddOnModulesButton(){
        LogHelper.substep("Clicking add on modules button");
        WebDriverHelper.clickElement(driver, ADD_ON_MODULES);
    }

    @Step("Select connected  element")
    public void clickConnectedElement(){
        LogHelper.substep("Select connected element");
        WebDriverHelper.clickElement(driver, CONNECTED_MODULE_ITEM);
    }

    @Step("Click add  button")
    public void clickAddButton(){
        LogHelper.substep("Click add button");
        WebDriverHelper.clickElement(driver, ADD_BUTTON);
    }

}
