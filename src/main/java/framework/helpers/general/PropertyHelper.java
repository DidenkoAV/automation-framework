package framework.helpers.general;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHelper {
    private static Properties properties = null;

    public static String initAndGetProperty(String file, String property){
        properties = initPropertyFile(file);
        return  getProperty(property);
    }

    private static Properties initPropertyFile(String propertyFileName)
    {
        try (InputStream is = PropertyHelper.class.getClassLoader().getResourceAsStream(propertyFileName))
        {
            properties.load(is);
        } catch (IOException e){
            throw new RuntimeException("[" + propertyFileName + "] can't be loaded");
        }
        return properties;
    }

    private static String getProperty(String propertyName)
    {
        return properties.getProperty(propertyName);
    }

}
