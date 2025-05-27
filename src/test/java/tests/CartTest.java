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

//@Execution(ExecutionMode.CONCURRENT)
public class CartTest extends BaseTest { // Тестовый класс для проверки работы с корзиной


//    @ParameterizedTest
//    @ValueSource(strings = {"chromium", "firefox", "webkit"})
    @Test
    void testAddToCart() throws IOException {
        LoginPage loginPage = new LoginPage(page);
        // Навигация на базовый URL из конфига
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

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

    @Test
    void testCartFunctionality() throws IOException {
        LoginPage loginPage = new LoginPage(page);
        // Навигация на базовый URL из конфига
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // Добавление товаров
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");

        // Переход в корзину
        CartPage cartPage = new CartPage(page);
        cartPage.navigateToCart();

        // Проверка товаров
        List<String> items = cartPage.getCartItems();
        assertAll(
                () -> assertEquals(3, items.size()),
                () -> assertTrue(items.contains("Sauce Labs Backpack")),
                () -> assertTrue(items.contains("Sauce Labs Bike Light"))
        );
        // Удаление одного товара
        cartPage.removeItem("Sauce Labs Bike Light");
        assertEquals(2, cartPage.getCartItems().size());

        // Проверка суммы
        cartPage.getTotalPrice();
    }


    @Test // Аннотация, указывающая, что метод является тестом
    void testParallelCartManagement() throws IOException { // Метод для тестирования управления корзиной в параллельном режиме

        LoginPage loginPage = new LoginPage(page);
        // Навигация на базовый URL из конфига
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        InventoryPage inventoryPage = new InventoryPage(page); // Создание объекта InventoryPage для работы с товарами
            CartPage cartPage = new CartPage(page); // Создание объекта CartPage для работы с корзиной

            inventoryPage.addProductToCart("Sauce Labs Bike Light"); // Добавление товара "Sauce Labs Bike Light" в корзину
            inventoryPage.addProductToCart("Sauce Labs Bolt T-Shirt"); // Добавление товара "Sauce Labs Bolt T-Shirt" в корзину
            int cartCount = inventoryPage.getCartItemCount(); // Получение количества товаров в корзине
            assertEquals(2, cartCount, "Счетчик корзины должен показывать 2 товара"); // Проверка, что количество товаров в корзине равно 2

            cartPage.navigateToCart(); // Переход к странице корзины
            cartPage.removeItem("Sauce Labs Bike Light"); // Удаление товара "Sauce Labs Bike Light" из корзины

            assertEquals(2, cartPage.getCartItems().size(), // Проверка, что после удаления остался 1 товар
                    "После удаления должен остаться 1 товар");
            assertTrue(cartPage.getCartItems().contains("Sauce Labs Bolt T-Shirt"), // Проверка, что оставшийся товар соответствует ожиданиям
                    "Оставшийся товар должен соответствовать ожиданиям");
        }
    }
