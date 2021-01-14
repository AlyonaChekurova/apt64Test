package pages;

import helpers.Waiters;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Start page object.
 */
public class StartPage {
    /**
     * Browser driver.
     */
    private WebDriver driver;

    /**
     * Maximum time to wait per seconds.
     */
    private final static int MAX_WAIT = 5;

    /**
     * Search input field.
     */
    @FindBy (css = ".input-search")
    private WebElement searchInput;

    /**
     * Button shows authorization form.
     */
    @FindBy (css = ".authorization.login-button")
    private WebElement authButton;

    /**
     * Authorization form window.
     */
    @FindBy (css = ".login-box.logout-box")
    private WebElement loginBox;

    /**
     * Login field.
     */
    @FindBy (css = "input[name = 'login']")
    private WebElement loginField;
    /**
     * Passwoed field.
     */
    @FindBy (css = "input[name = 'password']")
    private WebElement passwordField;

    /**
     * Login button.
     */
    @FindBy (css = "#signIn2")
    private WebElement loginButton;

    /**
     * Search results text.
     */
    @FindBy (css = ".search-word")
    private WebElement searchResult;

    /**
     * Add to cart button in the first search result.
     */
    @FindBy (css =
            ".search-block-name>.search-block>ul>li:nth-child(1)>div>.add-to-cart.buy")
    private WebElement addToCartButton;

    /**
     * Class constructor.
     * Initializes WebDriver instance.
     * @param webDriver - WebDriver instance
     */
    public StartPage(WebDriver webDriver){
        this.driver = webDriver;
        webDriver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(
                MAX_WAIT, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(
                MAX_WAIT, TimeUnit.SECONDS);

        PageFactory.initElements(webDriver, this);
    }

    /**
     * Click button to show authorization form method.
     * @return StartPage object
     */
    @Step("Нажать кнопку показа формы авторизации.")
    public final StartPage clickAuthButton() {
        Waiters.waitClickability(driver, MAX_WAIT, authButton);
        authButton.click();
        return this;
    }

    /**
     * Try to authorize method.
     * @param login - login for authorization
     * @param password - password for authorization
     * @return error message text
     */
    @Step("Авторизоваться с логином '{login}' и паролем '{password}'")
    public final String checkAuth(final String login,
                                     final String password) {
        Waiters.waitVisibility(driver, MAX_WAIT, loginBox);
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        Waiters.waitClickability(driver, MAX_WAIT, loginButton);
        loginButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector(".login-error"));
        Waiters.waitVisibility(driver, MAX_WAIT, errorMessage);

        return errorMessage.getText();
    }

    /**
     * Get search results method.
     * @param searchQuery - query to search for
     * @return StartPage object
     */
    @Step("Выполнить поиск по запросу {searchQuery}")
    public final StartPage getSearchResults(final String searchQuery) {
        searchInput.sendKeys(searchQuery + Keys.ENTER);
        Waiters.waitVisibility(driver, MAX_WAIT, searchResult);

        return this;
    }

    /**
     * Get search result text method.
     * @param searchQuery - query to search for
     * @return search result text
     */
    @Step("Получить текст о результатах поиска по запросу {searchQuery}")
    public final String getSearchResultText(final String searchQuery) {
        getSearchResults(searchQuery);

        return searchResult.getText();
    }

    /**
     * Click add to cart button method.
     * @param searchQuery - query to search for
     * @return StartPage object when count is changed
     */
    @Step("Выполнить поиск по запросу {searchQuery} и добавить в корзину товар, "
            + "первый по результатам поиска")
    public final StartPage clickAddToCartButton(final String searchQuery) {
        getSearchResults(searchQuery);
        Waiters.waitClickability(driver, MAX_WAIT, addToCartButton);
        addToCartButton.click();

        final String currentText = driver.findElement(By.cssSelector("#itogCount")).getText();
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT);
        wait.until((WebDriver driver) -> !driver.findElement(By.cssSelector("#itogCount"))
                .getText().equals(currentText));

        return this;
    }

    /**
     * Get cart count value method.
     * @return cart count value
     */
    @Step("Получить значение счетчика товаров в корзине")
    public final int getCartCount() {
        WebElement cartCount = driver.findElement(By.cssSelector("#itogCount"));

        return Integer.parseInt(cartCount.getText());
    }
}

