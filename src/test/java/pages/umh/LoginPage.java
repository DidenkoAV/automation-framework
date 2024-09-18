package pages.umh;

import framework.helpers.selenium.WebDriverHelper;
import framework.pageengine.InitPage;
import org.openqa.selenium.WebDriver;

public class LoginPage extends InitPage {
    private static final String COMPANY_INPUT = "//*[@id='branchInput']";
    private static final String USERNAME_INPUT = "//*[@id='emailInput']";
    private static final String PASSWORD_INPUT = "//*[@id='passwordInput']";
    private static final String LOGIN_BUTTON   = "//*[@class='btn btn-lg btn-primary btn-block mb-4']";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterCompany(String company){
        WebDriverHelper.enterTextByXpath(driver,company, COMPANY_INPUT);
    }

    public void enterUser(String user){
        WebDriverHelper.enterTextByXpath(driver,user, USERNAME_INPUT);
    }

    public void enterPassword(String password){
        WebDriverHelper.enterTextByXpath(driver,password, PASSWORD_INPUT);
    }

    public void clickLoginButton(){
        WebDriverHelper.clickElement(driver, LOGIN_BUTTON);
    }

}
