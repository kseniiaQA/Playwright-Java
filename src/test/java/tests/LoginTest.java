package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest extends BaseTest {


    @Test
    void testAddMultipleProductsToCart() {
        page.navigate("https://ginandjuice.shop");
        page.locator("a.account-icon").click();
        page.locator("input[name='username']").fill("carlos");
        page.locator("button:text('Log in')").click();
        page.locator("input[name='password']").fill("hunter2");
        page.locator("button:text('Log in')").click();
        page.locator("div.scanme-logo").click();
    }
}
