/**
 * 
 */
package core.pageengine;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.ui.Select;

public class CompanyFieldDecorator extends DefaultFieldDecorator implements FieldDecorator {

  public CompanyFieldDecorator(ElementLocatorFactory factory) {
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
    return (WebElement) Proxy.newProxyInstance(loader, new Class[]{
                    WebElement.class, WrapsElement.class, Locatable.class},
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
      return null; // Return null if no such element exists
    }
  }

  private boolean isDecoratableField(Field field) {
    return field.getAnnotation(FindBy.class) != null || field.getAnnotation(FindBys.class) != null;
  }

  public boolean isDecoratableList(Field field) {
    if (!List.class.isAssignableFrom(field.getType())) {
      return false;
    }

    Type genericType = field.getGenericType();
    return genericType instanceof ParameterizedType &&
            WebElement.class.equals(((ParameterizedType) genericType).getActualTypeArguments()[0]) &&
            isDecoratableField(field);
  }

  private boolean isSelect(Field field) {
    return Select.class.isAssignableFrom(field.getType());
  }
}
