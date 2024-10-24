package messagepoint.pages;

import framework.helpers.selenium.WebDriverHelper;
import framework.helpers.log.LogHelper;
import framework.pageengine.InitPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class LoginPage extends InitPage {

    private static final String COMPANY_INPUT = "//*[@id='branchInput']";
    private static final String USERNAME_INPUT = "//*[@id='emailInput']";
    private static final String PASSWORD_INPUT = "//*[@id='passwordInput']";
    private static final String LOGIN_BUTTON   = "//*[@class='btn btn-lg btn-primary btn-block mb-4']";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter company: {company}")
    public void enterCompany(String company){
        LogHelper.substep("Entering company:" + company);
        WebDriverHelper.enterTextByXpath(driver, company, COMPANY_INPUT);
    }

    @Step("Enter username: {user}")
    public void enterUser(String user){
        LogHelper.substep("Entering username:"+ user);
        WebDriverHelper.enterTextByXpath(driver, user, USERNAME_INPUT);
    }

    @Step("Enter password: {password}")
    public void enterPassword(String password){
        LogHelper.substep("Entering password:"+ password);
        WebDriverHelper.enterTextByXpath(driver, password, PASSWORD_INPUT);
    }

    @Step("Click login button")
    public void clickLoginButton(){
        LogHelper.substep("Clicking the login button");
        WebDriverHelper.clickElement(driver, LOGIN_BUTTON);
    }
}
