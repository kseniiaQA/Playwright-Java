package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class LoginPage { // Класс, инкапсулирующий логику работы со страницей авторизации

    private final Page page; // Экземпляр страницы Playwright
    private Properties properties = new Properties();

    // Метод для загрузки свойств из файла
    public void loadProperties() {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(fis);
            System.out.println("Файл конфигурации загружен успешно.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось загрузить файл конфигурации", e);
        }
    }

    // Метод для перехода на URL
    public void navigateToBaseUrl(Page page) {
        String baseUrl = properties.getProperty("baseUrl");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("baseUrl не задан в config.properties");
        }
        page.navigate(baseUrl);
    }

    // Конструктор класса
    public LoginPage(Page page) {
        this.page = page; // Инициализация объекта страницы
        loadProperties();
        navigateToBaseUrl(page);
        // Ожидание загрузки DOM-структуры страницы
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    // Метод для выполнения авторизации
    public void login(String username, String password) {
        // Ожидание появления полей ввода
        page.locator("input[data-test='username']").waitFor();
        page.locator("input[data-test='password']").waitFor();

        // Заполнение полей логина и пароля
        page.fill("input[data-test='username']", username); // Ввод имени пользователя
        page.fill("input[data-test='password']", password); // Ввод пароля

        // Клик по кнопке авторизации
        page.click("input[data-test='login-button']");

        // Ожидание перехода на целевую страницу после логина
        page.waitForURL(
                url -> url.contains("/inventory.html"), // Проверка целевого URL
                new Page.WaitForURLOptions().setTimeout(10000) // Таймаут 10 секунд
        );
    }
}