package tests;

import helpers.DriverFactory;
import helpers.ParametersProvider;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Test suite for start page.
 */
public class StartPageTitleTests {

    /**
     * Browser driver.
     */
    private WebDriver driver;

    /**
     * Suite setup.
     *
     * @throws IOException when config file is unavailable
     */

    @BeforeClass
    public final void setEnvironment() throws IOException {
        this.driver = DriverFactory.createDriver();
        String webUrl = ParametersProvider.getProperty("webUrl");
        driver.get(webUrl);
    }

    @Test(description = "Успешный тест: проверка корректного заголовка стартовой страницы")
    public final void checkCorrectMainPageTitle() throws IOException {
        String correctPageTitle = ParametersProvider.getProperty("correctTitle");
        String currentPageTitle = driver.getTitle();

        Assert.assertEquals(currentPageTitle, correctPageTitle);
    }

    @Test(description = "Успешный тест: проверка некорректного заголовка стартовой страницы")
    public final void checkWrongMainPageTitle() throws IOException {
        String wrongPageTitle = ParametersProvider.getProperty("wrongTitle");
        String currentPageTitle = driver.getTitle();

        Assert.assertEquals(currentPageTitle, wrongPageTitle);
    }

    /**
     * Suite teardown.
     */
    @AfterClass
    public final void tearDown() {
        driver.quit();
    }

}

