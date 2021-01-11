package helpers;


import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

/**
 * Helper class for page objects.
 */
public final class PageObjectUtils {
    /**
     * Not called.
     */
    private PageObjectUtils() {
    }

    /**
     * Wait page is open by its Url.
     * @param driver browser driver
     * @param pageUrl page URL
     * @throws IOException when config file is not available
     */
    public static void waitPageLoad(final WebDriver driver,
                                    final String pageUrl)
            throws IOException {
        int timeout = Integer.parseInt(ParametersProvider
                .getProperty("explicitTimeout"));
        Waiters.waitUntilAngularReady(driver, timeout);
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.urlContains(pageUrl));
    }

    /**
     * Checks that page is open by its Url.
     * @param driver browser driver
     * @param pageUrl page URL
     * @return is page open
     * @throws IOException when config file is not available
     */
    public static boolean checkPageIsPresentByUrl(final WebDriver driver,
                                                  final String pageUrl)
            throws IOException {
        try {
            waitPageLoad(driver, pageUrl);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
}

