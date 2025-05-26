package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;
    private final Locator loginIcon;
    private final Locator username;
    private final Locator submitButton;
    private final Locator password;
    private final Locator logo;

    public LoginPage(Page page) {
        this.page = page;
        this.loginIcon = page.locator("a.account-icon");
        this.username = page.locator("input[name='username']");
        this.submitButton = page.locator("button:text('Log in')");
        this.password = page.locator("input[name='password']");
        this.logo = page.locator("div.scanme-logo");
    }

    public void login(String usernameStr, String passwordStr) {
        loginIcon.click();
        username.fill(usernameStr);
        submitButton.click();
        password.fill(passwordStr);
        submitButton.click();
        logo.click();
    }

    public void incorrectLogin(String usernameStr, String passwordStr) {
        loginIcon.click();
        username.fill(usernameStr);
        submitButton.click();
        password.fill(passwordStr);
        submitButton.click();
    }
}
