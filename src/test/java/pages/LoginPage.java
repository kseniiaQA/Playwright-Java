package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import config.ConfigReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginPage {

    private final Page page;

    private final String usernameSelector;
    private final String passwordSelector;
    private final String loginButtonSelector;

    public LoginPage(Page page) throws IOException {
        this.page = page;

        Properties selectors = new Properties();
        selectors.load(new FileInputStream("src/test/resources/selectors.properties"));

        usernameSelector = selectors.getProperty("usernameSelector");
        passwordSelector = selectors.getProperty("passwordSelector");
        loginButtonSelector = selectors.getProperty("loginButtonSelector");
        String baseUrl = ConfigReader.getProperty("baseUrl");
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }


    public void login(String username, String password) {
        page.locator(usernameSelector).waitFor();
        page.locator(passwordSelector).waitFor();
        page.fill(usernameSelector, username);
        page.fill(passwordSelector, password);
        page.click(loginButtonSelector);
    }
}
