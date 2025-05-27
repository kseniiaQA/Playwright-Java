package base;

import com.microsoft.playwright.Page;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotWatcher implements TestWatcher { // Объявление класса ScreenshotWatcher, который реализует интерфейс TestWatcher для отслеживания результатов тестов

    @Getter // Аннотация для автоматической генерации метода получения значения lastScreenshotPath
    private static String lastScreenshotPath; // Статическая переменная для хранения пути к последнему скриншоту
    private static final ThreadLocal<Page> pageHolder = new ThreadLocal<>(); // ThreadLocal для хранения объекта Page, специфичного для текущего потока

    @Override // Переопределение метода testFailed из интерфейса TestWatcher
    public void testFailed(ExtensionContext context, Throwable cause) { // Метод, вызываемый при неудаче теста
        Page page = pageHolder.get(); // Получение объекта Page из ThreadLocal
        if (page != null) { // Проверка, что объект Page не равен null
            String testName = context.getDisplayName().replaceAll("[^a-zA-Z0-9]", "_"); // Получение имени теста и замена недопустимых символов на "_"
            Path path = Paths.get("target/screenshots", testName + "_FAILED.png"); // Формирование пути для сохранения скриншота
            try {
                page.screenshot(new Page.ScreenshotOptions().setPath(path)); // Создание скриншота и сохранение его по указанному пути
                lastScreenshotPath = path.toString(); // Сохранение пути к последнему скриншоту
            } catch (Exception e) { // Обработка исключений на случай ошибки при создании скриншота
                System.err.println("Ошибка при создании скриншота: " + e.getMessage()); // Вывод сообщения об ошибке в стандартный поток ошибок
            }
        }
    }
}