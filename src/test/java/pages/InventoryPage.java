package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class InventoryPage { // Класс для работы со страницей товаров

    private final Page page; // Экземпляр страницы Playwright

    // Конструктор с проверкой готовности страницы
    public InventoryPage(Page page) {
        this.page = page;
        // Ожидание видимости списка товаров с таймаутом 10 сек
        page.waitForSelector(".inventory_list",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000));
    }

    // Метод добавления товара в корзину по имени
    public void addProductToCart(String productName) {
        // Динамический локатор с проверкой названия товара
        page.locator(String.format(
                        "div.inventory_item:has-text('%s') button:has-text('Add to cart')",
                        productName))
                .click(); // Клик по кнопке добавления
    }

    // Метод получения количества товаров в корзине
    public int getCartItemCount() {
        try {
            // Парсинг числового значения из корзины
            return Integer.parseInt(page.textContent(".shopping_cart_badge"));
        } catch (Exception e) { // Обработка случая с пустой корзиной
            return 0; // Возврат 0 если пустой
        }
    }
}
