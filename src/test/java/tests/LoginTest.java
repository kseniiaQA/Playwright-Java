package tests;

import base.BaseTest;
import config.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pages.CartPage;
import pages.LoginPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private CartPage cartPage;

    @BeforeEach
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(page);
        cartPage = new CartPage(page);
    }

    @Test
    void testAddMultipleProductsToCart() {
        page.navigate(Config.BASE_URL);
        loginPage.login("carlos", "hunter2");
        cartPage.addToCart();
    }
}
