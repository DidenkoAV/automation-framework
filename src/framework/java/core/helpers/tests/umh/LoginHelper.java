package core.helpers.tests.umh;

import core.pages.LoginPage;
import core.tdo.umh.LoginTdo;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class LoginHelper {
    public static LoginTdo setupLoginTdo(HashMap<String, String> data){
        LoginTdo loginTdo = new LoginTdo();
        loginTdo.setCompany(data.get("company"));
        loginTdo.setUser(data.get("user"));
        loginTdo.setPassword(data.get("password"));
        loginTdo.setTitle(data.get("title"));
        return loginTdo;

    }
    public static void login(WebDriver driver, LoginTdo tdo){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCompany(tdo.getCompany());
        loginPage.enterUser(tdo.getUser());
        loginPage.enterPassword(tdo.getPassword());
        loginPage.clickLoginButton();
    }
}
