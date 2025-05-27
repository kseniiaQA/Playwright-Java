package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class InventoryPage {

    private final Page page;

    // Конструктор с проверкой готовности страницы
    public InventoryPage(Page page) {
        this.page = page;
        page.waitForSelector(".inventory_list",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000));
    }

    // Метод добавления товара в корзину по имени
    public void addProductToCart(String productName) {
        page.locator(String.format(
                        "div.inventory_item:has-text('%s') button:has-text('Add to cart')",
                        productName))
                .click();
    }

    // Метод получения количества товаров в корзине
    public int getCartItemCount() {
        try {
            return Integer.parseInt(page.textContent(".shopping_cart_badge"));
        } catch (Exception e) {
            return 0;
        }
    }
}
