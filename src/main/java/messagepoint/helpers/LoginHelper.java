package messagepoint.helpers;

import messagepoint.tdo.umh.LoginTdo;
import framework.helpers.log.LogHelper;
import messagepoint.pages.LoginPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class LoginHelper {

    public static LoginTdo setupLoginTdo(HashMap<String, String> data) {
        LoginTdo loginTdo = new LoginTdo();
        loginTdo.setCompany(data.get("messagepoint"));
        loginTdo.setUser(data.get("user"));
        loginTdo.setPassword(data.get("password"));
        loginTdo.setTitle(data.get("title"));

        LogHelper.step("Prepare login tdo: company is [" + loginTdo.getCompany() + "], user is [" + loginTdo.getUser()+ "]");

        return loginTdo;
    }

    @Step("Start to login to UMH")
    public static void login(WebDriver driver, LoginTdo tdo) {
        LoginPage loginPage = new LoginPage(driver);

        LogHelper.step("Start to login:");

        loginPage.enterCompany(tdo.getCompany());
        loginPage.enterUser(tdo.getUser());
        loginPage.enterPassword(tdo.getPassword());
        loginPage.clickLoginButton();
    }
}
