package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartPage {
    private final Page page;
    private final Locator addToCart;
    private final Locator product;
    private final Locator cartIcon;
    private final Locator price;

    public CartPage(Page page) {
        this.page = page;
        this.addToCart = page.locator("button:text('Add to cart')");
        this.product = page.locator("h3:text('Pineapple Edition Cocktail')");
        this.cartIcon =  page.locator("a.cart-icon");
        this.price =page.locator("strong:text('$30.50')");
    }



    public void addToCart() {
        product.click();
        addToCart.waitFor(new Locator.WaitForOptions().setTimeout(3000));
        addToCart.click();
        cartIcon.click();
        price.waitFor(new Locator.WaitForOptions().setTimeout(3000));
        assertTrue(price.isVisible(), "Текст '$30.50' виден на странице");
    }
}
