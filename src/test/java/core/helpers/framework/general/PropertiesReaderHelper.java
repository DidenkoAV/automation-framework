package core.helpers.framework.general;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReaderHelper {
    private Properties properties;

    public PropertiesReaderHelper(String propertyFileName)
    {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(propertyFileName))
        {
            this.properties = new Properties();
            this.properties.load(is);
        } catch (IOException e){
            throw new RuntimeException("[" + propertyFileName + "] can't be loaded");
        }
    }

    public String getProperty(String propertyName)
    {
        return this.properties.getProperty(propertyName);
    }

}
