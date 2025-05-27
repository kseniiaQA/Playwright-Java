package tests;

import base.BaseTest;
import com.microsoft.playwright.Page;
import config.ConfigReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;


@Execution(ExecutionMode.CONCURRENT)
public class CheckoutTest extends BaseTest {


    @ParameterizedTest
    @ValueSource(strings = {"chromium", "firefox", "webkit"})
    @Test
    void testSuccessfulCheckout() throws IOException {
        // Авторизация и добавление товара
        LoginPage loginPage = new LoginPage(page);
        // Навигация на базовый URL из конфига
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );
        new InventoryPage(page).addProductToCart("Sauce Labs Backpack");

        // Оформление заказа
        CartPage cartPage = new CartPage(page);
        cartPage.navigateToCart();
        cartPage.proceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(page);
        checkoutPage.fillShippingInfo("John", "Doe", "12345");
        checkoutPage.completePurchase();

        // Проверка подтверждения
        assertAll(
                () -> assertTrue(checkoutPage.isOrderConfirmed()),
                () -> assertEquals("Thank you for your order!", checkoutPage.getConfirmationMessage())
        );
    }


    @Test // Аннотация, указывающая, что метод является тестом
    void testParallelCheckoutProcess() throws IOException { // Метод для тестирования процесса оформления заказа в параллельном режиме
        LoginPage loginPage = new LoginPage(page);
        // Навигация на базовый URL из конфига
        page.navigate(ConfigReader.getProperty("baseUrl"));
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        assertTrue(page.url().contains("/inventory.html"), // Проверка, что URL содержит "/inventory.html" после успешного входа
                "Не удалось авторизоваться"); // Сообщение об ошибке, если проверка не прошла

        InventoryPage inventoryPage = new InventoryPage(page); // Создание объекта InventoryPage для работы с товарами
        CartPage cartPage = new CartPage(page); // Создание объекта CartPage для работы с корзиной
        CheckoutPage checkoutPage; // Объявление переменной для CheckoutPage

        inventoryPage.addProductToCart("Sauce Labs Backpack"); // Добавление товара "Sauce Labs Backpack" в корзину
        page.waitForCondition( // Ожидание, пока количество товаров в корзине не станет равным 1
                () -> inventoryPage.getCartItemCount() == 1, // Условие: количество товаров в корзине должно быть 1
                new Page.WaitForConditionOptions().setTimeout(10000) // Таймаут ожидания в 10000 мс
        );

        cartPage.navigateToCart(); // Переход к странице корзины
        page.waitForURL("**/cart.html"); // Ожидание, пока URL не изменится на "/cart.html"

        cartPage.proceedToCheckout(); // Переход к оформлению заказа

        checkoutPage = new CheckoutPage(page); // Создание объекта CheckoutPage для работы с оформлением заказа

        checkoutPage.fillShippingInfo("John", "Doe", "12345"); // Заполнение информации о доставке

        page.waitForURL("**/checkout-step-two.html"); // Ожидание, пока URL не изменится на "/checkout-step-two.html"

        checkoutPage.completePurchase(); // Завершение покупки
        assertTrue(page.url().contains("/checkout-complete.html")); // Проверка, что URL содержит "/checkout-complete.html" после завершения покупки
    }
}