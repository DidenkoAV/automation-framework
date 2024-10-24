package core.helpers.tests.umh;

import core.helpers.framework.log.LogHelper;
import core.pages.LoginPage;
import core.tdo.umh.LoginDto;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class LoginHelper {

    public static LoginDto setupLoginTdo(HashMap<String, String> data) {
        LoginDto loginDTO = new LoginDto();
        loginDTO.setCompany(data.get("company"));
        loginDTO.setUser(data.get("user"));
        loginDTO.setPassword(data.get("password"));
        loginDTO.setTitle(data.get("title"));

        LogHelper.step("Prepare login tdo: company is [" + loginDTO.getCompany() + "], user is [" + loginDTO.getUser()+ "]");

        return loginDTO;
    }

    @Step("Start to login to UMH")
    public static void login(WebDriver driver, LoginDto tdo) {
        LoginPage loginPage = new LoginPage(driver);

        LogHelper.step("Start to login:");

        loginPage.enterCompany(tdo.getCompany());
        loginPage.enterUser(tdo.getUser());
        loginPage.enterPassword(tdo.getPassword());
        loginPage.clickLoginButton();
    }
}
