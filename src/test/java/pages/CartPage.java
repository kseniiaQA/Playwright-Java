package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

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


        public void getCartItems() {
        assertTrue(price.isVisible(), "Текст 'Sauce Labs Backpack' виден на странице");
    }
}
