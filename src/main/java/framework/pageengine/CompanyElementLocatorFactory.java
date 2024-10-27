/**
 *
 */
package framework.pageengine;

import java.lang.reflect.Field;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class CompanyElementLocatorFactory implements ElementLocatorFactory {

    private final SearchContext searchContext;
    private final int ajaxTimeOutInSeconds;

    public CompanyElementLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
        this.ajaxTimeOutInSeconds = 5;
    }

    @Override
    public ElementLocator createLocator(Field field) {
        AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(searchContext, ajaxTimeOutInSeconds);
        return ajaxFactory.createLocator(field);
    }

}
