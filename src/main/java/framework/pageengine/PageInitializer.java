package framework.pageengine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class PageInitializer extends PageFactory
{

    public static <T> T initElements(WebDriver driver, Class<T> pageClassToProxy) {
        T page = instantiatePage(driver, pageClassToProxy);
        initElements(driver, page);
        return page;
    }

    public static void initElements(WebDriver driver, Object page) {
        initElements(new CompanyElementLocatorFactory(driver), page);
    }

    public static void initElements(ElementLocatorFactory factory, Object page) {
        initElements(new CompanyFieldDecorator(factory), page);
    }

    private static <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {
        try {
            Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
            return constructor.newInstance(driver);
        } catch (NoSuchMethodException e) {
            return createInstanceWithoutDriver(pageClassToProxy);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T createInstanceWithoutDriver(Class<T> pageClassToProxy) {
        try {
            return pageClassToProxy.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
