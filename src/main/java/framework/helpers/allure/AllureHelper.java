package framework.helpers.allure;
import framework.helpers.general.FileHelper;
import framework.helpers.general.PropertiesReaderHelper;

import java.io.File;

import static framework.constants.GeneralConstants.ALLURE_DIRECTORY_KEY;
import static framework.constants.GeneralConstants.ALLURE_PROPERTIES;


public class AllureHelper {


    public static void clearAllureFolder(){
        PropertiesReaderHelper helper = new PropertiesReaderHelper(ALLURE_PROPERTIES);
        String allureDir = helper.getProperty(ALLURE_DIRECTORY_KEY);
        FileHelper.deleteDirectory(new File(allureDir));
    }
}
