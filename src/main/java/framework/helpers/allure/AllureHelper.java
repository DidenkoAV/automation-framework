package framework.helpers.allure;
import framework.helpers.general.FileHelper;
import framework.helpers.general.PropertyHelper;

import java.io.File;

import static framework.constants.GeneralConstants.ALLURE_DIRECTORY_KEY;
import static framework.constants.GeneralConstants.ALLURE_PROPERTIES;


public class AllureHelper {


    public static void clearAllureFolder(){
        String allureDir = PropertyHelper.initAndGetProperty(ALLURE_PROPERTIES,ALLURE_DIRECTORY_KEY);
        FileHelper.deleteDirectory(new File(allureDir));
    }
}
