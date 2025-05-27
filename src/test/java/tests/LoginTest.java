package tests;

import base.BaseTest;
import config.ConfigReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.InventoryPage;
import pages.LoginPage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
public class LoginTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(strings = {"chromium", "firefox", "webkit"})
    @Test
    void testSuccessfulLogin() throws IOException {
        LoginPage loginPage = new LoginPage(page);
        // Навигация на базовый URL из конфига
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        assertTrue(page.url().contains("/inventory.html"), "URL после логина не содержит /inventory.html");

        InventoryPage inventoryPage = new InventoryPage(page);
        assertTrue(inventoryPage.getCartItemCount() >= 0, "Количество товаров в корзине должно быть >= 0");
    }
}