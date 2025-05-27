package pages;

import com.microsoft.playwright.Page;

public class CheckoutPage { // Объявление класса CheckoutPage, который представляет страницу оформления заказа
    private final Page page; // Объявление переменной page типа Page для взаимодействия с элементами страницы
    private final String FIRST_NAME_INPUT = "#first-name"; // CSS-селектор для поля ввода имени
    private final String LAST_NAME_INPUT = "#last-name"; // CSS-селектор для поля ввода фамилии
    private final String POSTAL_CODE_INPUT = "#postal-code"; // CSS-селектор для поля ввода почтового кода
    private final String CONTINUE_BUTTON = "#continue"; // CSS-селектор для кнопки продолжения
    private final String FINISH_BUTTON = "#finish"; // CSS-селектор для кнопки завершения покупки

    public CheckoutPage(Page page) { // Конструктор класса, принимающий объект Page в качестве параметра
        this.page = page; // Инициализация переменной page переданным объектом
    }

    public void fillShippingInfo(String firstName, String lastName, String postalCode) { // Метод для заполнения информации о доставке
        page.fill(FIRST_NAME_INPUT, firstName); // Заполнение поля имени
        page.fill(LAST_NAME_INPUT, lastName); // Заполнение поля фамилии
        page.fill(POSTAL_CODE_INPUT, postalCode); // Заполнение поля почтового кода
        page.click(CONTINUE_BUTTON); // Клик по кнопке продолжения для перехода к следующему шагу
    }

    public void completePurchase() { // Метод для завершения покупки
        page.click(FINISH_BUTTON); // Клик по кнопке завершения покупки
    }

    public boolean isOrderConfirmed() { // Метод для проверки, подтвержден ли заказ
        return page.isVisible(".complete-header"); // Возвращает true, если элемент с классом .complete-header видим (заказ подтвержден)
    }

    public String getConfirmationMessage() { // Метод для получения сообщения о подтверждении заказа
        return page.textContent(".complete-header"); // Возвращает текстовое содержимое элемента с классом .complete-header
    }
}