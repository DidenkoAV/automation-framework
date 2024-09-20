package core.helpers.tests.umh;

import core.pages.LoginPage;
import core.dto.umh.LoginDto;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class LoginHelper {
    private static final Logger logger = LoggerFactory.getLogger(LoginHelper.class);

    public static LoginDto setupLoginTdo(HashMap<String, String> data) {
        LoginDto loginDTO = new LoginDto();
        loginDTO.setCompany(data.get("company"));
        loginDTO.setUser(data.get("user"));
        loginDTO.setPassword(data.get("password"));
        loginDTO.setTitle(data.get("title"));

        logger.info("Setting up LoginDto with company: {}, user: {}, title: {}",
                loginDTO.getCompany(), loginDTO.getUser(), loginDTO.getTitle());

        return loginDTO;
    }

    @Step("Start to login to UMH")
    public static void login(WebDriver driver, LoginDto tdo) {
        LoginPage loginPage = new LoginPage(driver);

        logger.info("Logging in with company: {}, user: {}", tdo.getCompany(), tdo.getUser());

        loginPage.enterCompany(tdo.getCompany());
        loginPage.enterUser(tdo.getUser());
        loginPage.enterPassword(tdo.getPassword());
        loginPage.clickLoginButton();

        logger.info("Login attempt completed for user: {}", tdo.getUser());
    }
}
