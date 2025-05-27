package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import config.ConfigReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginPage {

    private final Page page;

    // Локаторы элементов на странице логина
    private final String usernameSelector;
    private final String passwordSelector;
    private final String loginButtonSelector;


    // Конструктор класса
    public LoginPage(Page page) throws IOException {
        this.page = page;

        // Загружаем селекторы из selectors.properties
        Properties selectors = new Properties();
        selectors.load(new FileInputStream("src/test/resources/selectors.properties"));

        // Инициализация локаторов
        usernameSelector = selectors.getProperty("usernameSelector");
        passwordSelector = selectors.getProperty("passwordSelector");
        loginButtonSelector = selectors.getProperty("loginButtonSelector");

        // Загружаем базовые данные из config.properties
        String baseUrl = ConfigReader.getProperty("baseUrl");

        // Навигация на базовый URL
        page.navigate(baseUrl);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    // Метод для выполнения логина
    public void login(String username, String password) {
        // Ожидание наличия элементов на странице
        page.locator(usernameSelector).waitFor();
        page.locator(passwordSelector).waitFor();

        // Заполнение полей логина
        page.fill(usernameSelector, username);
        page.fill(passwordSelector, password);

        // Клик по кнопке логина
        page.click(loginButtonSelector);

    }
}
