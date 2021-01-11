package helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

/**
 * Helper class for custom explicit waits.
 */
public final class Waiters {
    /**
     * Not called.
     */
    private Waiters() {
    }

    /**
     * Wait for Angular pending requests to finish.
     * @param webDriver browser driver
     * @param timeout wait threshold
     */
    public static void waitUntilAngularReady(final WebDriver webDriver,
                                             final int timeout) {
        final String angularReady =
                "return angular.element(document).injector()"
                        + ".get('$http').pendingRequests.length === 0";
        ExpectedCondition<Boolean> angularLoad = driver ->
                Boolean.valueOf(((JavascriptExecutor) driver)
                        .executeScript(angularReady).toString());
        new WebDriverWait(webDriver, timeout).until(angularLoad);
    }

    /**
     * Wait for Angular pending requests to finish.
     * @param webDriver browser driver
     * @throws IOException when config file is not available
     */
    public static void waitUntilAngularReady(final WebDriver webDriver)
            throws IOException {
        int explicitTimeout = Integer.parseInt(ParametersProvider
                .getProperty("explicitTimeout"));
        waitUntilAngularReady(webDriver, explicitTimeout);
    }
}


