package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {
    private final Page page;
    private final Locator addToCart;
    private final Locator product;

    public CartPage(Page page) {
        this.page = page;
        this.addToCart = page.locator("button:text('Add to cart')");
        this.product = page.locator("h3:text('Pineapple Edition Cocktail')");
    }

    public void addToCart() {
        product.click();
        addToCart.waitFor(new Locator.WaitForOptions().setTimeout(3000));
        addToCart.click();
    }
}
