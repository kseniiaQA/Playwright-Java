package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

@Execution(ExecutionMode.CONCURRENT)
public class CartTest extends BaseTest { // Тестовый класс для проверки работы с корзиной


    @ParameterizedTest
    @ValueSource(strings = {"chromium", "firefox", "webkit"})
    @Test
    void testAddToCart() {
        // Инициализация страницы авторизации
        LoginPage loginPage = new LoginPage(page);

        // Выполнение авторизации с валидными данными
        loginPage.login("standard_user", "secret_sauce");

        // Переход на страницу товаров
        InventoryPage inventoryPage = new InventoryPage(page);
        // Добавление конкретного товара в корзину
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        // Инициализация страницы корзины
        CartPage cartPage = new CartPage(page);
        // Переход в корзину
        cartPage.navigateToCart();

        // Проверка наличия добавленного товара в корзине
        cartPage.getCartItems();
    }
}