package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.List;


public class CartPage {
    private final Page page;
    private final Locator addToCart;
    private final Locator cartIcon;
    private final Locator price;

    public CartPage(Page page) {
        this.page = page;
        this.addToCart = page.locator("button:text('Add to cart')").nth(0);
        this.cartIcon =  page.locator("div.shopping_cart_container");
        this.price =page.locator("div:text('Sauce Labs Backpack')");
    }


    public void navigateToCart() {
        addToCart.waitFor(new Locator.WaitForOptions().setTimeout(3000));
        addToCart.click();
        cartIcon.click();
    }

    public void getTotalPrice() { // Метод для перехода к оформлению заказа
        page.locator("div:text('$29.99')").isVisible(); // Клик по кнопке с атрибутом data-test='checkout' для начала процесса оформления

    }

    public void removeItem(String productName) { // Метод для удаления товара из корзины по его имени
        page.locator(".cart_item:has-text('" + productName + "') button").click(); // Находим кнопку удаления товара и кликаем по ней
        page.waitForCondition( // Ожидаем, пока товар не исчезнет из корзины
                () -> !page.isVisible(".cart_item:has-text('" + productName + "')"), // Условие: товар не должен быть видим
                new Page.WaitForConditionOptions().setTimeout(5000) // Устанавливаем таймаут в 5000 мс для ожидания
        );
    }

    public List<String> getCartItems() { // Метод для получения списка товаров в корзине
        return page.locator(".inventory_item_name").allTextContents(); // Возвращаем все текстовые содержимое элементов с классом .inventory_item_name
    }

    public void proceedToCheckout() { // Метод для перехода к оформлению заказа
        page.click("button[data-test='checkout']"); // Клик по кнопке с атрибутом data-test='checkout' для начала процесса оформления
    }
}
