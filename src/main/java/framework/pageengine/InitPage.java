package framework.pageengine;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.*;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.List;

public class InitPage {
    protected WebDriver driver;

    public InitPage(WebDriver driver) {
        this.driver = driver;
        initElements();
    }

    private void initElements() {
        ElementLocatorFactory factory = new CustomElementLocatorFactory(driver, 5);
        FieldDecorator decorator = new CustomFieldDecorator(factory);
        PageFactory.initElements(decorator, this);
    }

    private static class CustomFieldDecorator extends DefaultFieldDecorator {
        public CustomFieldDecorator(ElementLocatorFactory factory) {
            super(factory);
        }

        @Override
        public Object decorate(ClassLoader loader, Field field) {
            if (!isDecoratableField(field)) {
                return null;
            }

            ElementLocator locator = factory.createLocator(field);
            if (locator == null) {
                return null;
            }

            if (isSelect(field)) {
                return createSelectProxy(loader, locator);
            } else if (WebElement.class.isAssignableFrom(field.getType())) {
                return createWebElementProxy(loader, locator);
            } else if (List.class.isAssignableFrom(field.getType())) {
                return createListProxy(loader, locator);
            }

            return null;
        }

        private WebElement createWebElementProxy(ClassLoader loader, ElementLocator locator) {
            return (WebElement) Proxy.newProxyInstance(loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class},
                    new LocatingElementHandler(locator));
        }

        @SuppressWarnings("unchecked")
        private List<WebElement> createListProxy(ClassLoader loader, ElementLocator locator) {
            return (List<WebElement>) Proxy.newProxyInstance(loader, new Class[]{List.class},
                    new LocatingElementHandler(locator));
        }

        private Select createSelectProxy(ClassLoader loader, ElementLocator locator) {
            WebElement element = createWebElementProxy(loader, locator);
            try {
                return new Select(element);
            } catch (NoSuchElementException e) {
                return null;
            }
        }

        private boolean isDecoratableField(Field field) {
            return field.getAnnotation(FindBy.class) != null || field.getAnnotation(FindBys.class) != null;
        }

        private boolean isSelect(Field field) {
            return Select.class.isAssignableFrom(field.getType());
        }
    }

    // Custom Element Locator Factory with Ajax support
    private static class CustomElementLocatorFactory implements ElementLocatorFactory {
        private final SearchContext searchContext;
        private final int timeout;

        public CustomElementLocatorFactory(SearchContext searchContext, int timeout) {
            this.searchContext = searchContext;
            this.timeout = timeout;
        }

        @Override
        public ElementLocator createLocator(Field field) {
            return new AjaxElementLocatorFactory(searchContext, timeout).createLocator(field);
        }
    }
}
