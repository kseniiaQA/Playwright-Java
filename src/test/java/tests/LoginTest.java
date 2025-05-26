package tests;

import base.BaseTest;
import config.Config;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private CartPage cartPage;

    @BeforeEach
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(page);
        cartPage = new CartPage(page);
    }


    @Epic("Авторизация")
    @Feature("Логин на сайт")
    @Story("Проверка входа с некорректными данными")
    @Test
    void testIncorrectCreds() {
        page.navigate(Config.BASE_URL);
        loginPage.incorrectLogin("test", "test");
        page.locator("is-warning:text('Invalid username or password.')").isVisible();
    }
}
