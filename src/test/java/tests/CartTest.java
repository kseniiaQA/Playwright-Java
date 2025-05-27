package tests;

import base.BaseTest;
import config.ConfigReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class CartTest extends BaseTest { // Тестовый класс для проверки работы с корзиной


    @ParameterizedTest
    @ValueSource(strings = {"chromium", "firefox", "webkit"})
    @Test
    void testAddToCart() throws IOException {
        LoginPage loginPage = new LoginPage(page);
        // Навигация на базовый URL из конфига
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        CartPage cartPage = new CartPage(page);
        cartPage.navigateToCart();

        cartPage.getCartItems();
    }

    @Test
    void testCartFunctionality() throws IOException {
        LoginPage loginPage = new LoginPage(page);
        // Навигация на базовый URL из конфига
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");

        CartPage cartPage = new CartPage(page);
        cartPage.navigateToCart();

        List<String> items = cartPage.getCartItems();
        assertAll(
                () -> assertEquals(2, items.size()),
                () -> assertTrue(items.contains("Sauce Labs Backpack")),
                () -> assertTrue(items.contains("Sauce Labs Bike Light"))
        );
        cartPage.removeItem();
        assertEquals(1, cartPage.getCartItems().size());
        cartPage.getTotalPrice();
    }


    @Test
    void testParallelCartManagement() throws IOException {
        LoginPage loginPage = new LoginPage(page);
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        InventoryPage inventoryPage = new InventoryPage(page);
            CartPage cartPage = new CartPage(page);

            inventoryPage.addProductToCart("Sauce Labs Bike Light");
            inventoryPage.addProductToCart("Sauce Labs Bolt T-Shirt");
            int cartCount = inventoryPage.getCartItemCount();
            assertEquals(2, cartCount, "Счетчик корзины должен показывать 2 товара");

            cartPage.navigateToCart();
            cartPage.removeItem();

            assertEquals(1, cartPage.getCartItems().size(),
                    "После удаления должен остаться 1 товар");
            assertTrue(cartPage.getCartItems().contains("Sauce Labs Bolt T-Shirt"),
                    "Оставшийся товар должен соответствовать ожиданиям");
        }
    }
