package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.List;

public class CartPage {
    private final Page page;
    private final Properties selectors = new Properties();
    private final Locator addToCart;
    private final Locator cartIcon;
    private final Locator price;

    public CartPage(Page page) {
        this.page = page;
        loadSelectors();

        // Инициализация локаторов с использованием загруженных селекторов
        this.addToCart = page.locator(selectors.getProperty("addToCart"));
        this.cartIcon = page.locator(selectors.getProperty("cartIcon"));
        this.price = page.locator(selectors.getProperty("price"));
    }

    private void loadSelectors() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("selectors.properties")) {
            if (input == null) {
                throw new RuntimeException("Не найден файл selectors.properties в classpath");
            }
            selectors.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке selectors.properties", e);
        }
    }

    public void navigateToCart() {
        cartIcon.click();
    }

    public void getTotalPrice() {
        page.locator("div:text('$29.99')").isVisible(); // Проверка видимости цены
    }

    public void removeItem() {
        page.click(selectors.getProperty("remove"));

    }

    public List<String> getCartItems() {
        return page.locator(selectors.getProperty("inventoryItemName")).allTextContents(); // Возвращаем все текстовые содержимое элементов с классом .inventory_item_name
    }

    public void proceedToCheckout() {
        page.click(selectors.getProperty("checkoutButton")); // Клик по кнопке оформления заказа
    }
}
