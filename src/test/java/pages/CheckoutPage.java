package pages;

import com.microsoft.playwright.Page;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CheckoutPage {
    private final Page page;
    private final Properties selectors = new Properties();

    public CheckoutPage(Page page) {
        this.page = page;
        loadSelectors();
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

    public void fillShippingInfo(String firstName, String lastName, String postalCode) {
        page.fill(selectors.getProperty("firstNameInput"), firstName); // Заполнение поля имени
        page.fill(selectors.getProperty("lastNameInput"), lastName); // Заполнение поля фамилии
        page.fill(selectors.getProperty("postalCodeInput"), postalCode); // Заполнение поля почтового кода
        page.click(selectors.getProperty("continueButton")); // Клик по кнопке продолжения
    }

    public void completePurchase() {
        page.click(selectors.getProperty("finishButton")); // Клик по кнопке завершения покупки
    }

    public boolean isOrderConfirmed() {
        return page.isVisible(selectors.getProperty("completeHeader")); // Проверка видимости заголовка подтверждения заказа
    }

    public String getConfirmationMessage() {
        return page.textContent(selectors.getProperty("completeHeader")); // Получение текста сообщения о подтверждении заказа
    }
}
