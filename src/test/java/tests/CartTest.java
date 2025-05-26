package tests;

import base.BaseTest;
import config.Config;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pages.CartPage;
import pages.LoginPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private CartPage cartPage;

    @BeforeEach
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(page);
        cartPage = new CartPage(page);
    }

    @Test
    @Description("Проверка добавления товара в корзину и соответствия цены")
    void testCheckPriceForAddedProduct() {
        navigateToHomePage();
        login("carlos", "hunter2");
        addToCart();
    }

    @Step("Навигация на главную страницу")
    void navigateToHomePage() {
        page.navigate(Config.BASE_URL);
    }

    @Step("Вход в систему с пользователем {username}")
    void login(String username, String password) {
        loginPage.login(username, password);
    }

    @Step("Добавление товара в корзину")
    void addToCart() {
        cartPage.addToCart();
    }

}
