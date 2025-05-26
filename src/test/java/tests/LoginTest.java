package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
public class LoginTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(strings = {"chromium", "firefox", "webkit"})
    @Test
    void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "secret_sauce");

        // Явная проверка URL
        assertTrue(page.url().contains("/inventory.html"));

        // Дополнительные проверки элементов страницы
        InventoryPage inventoryPage = new InventoryPage(page); // Проверяет загрузку в конструкторе
        assertTrue(inventoryPage.getCartItemCount() >= 0); // Проверка инициализации корзины
    }
}