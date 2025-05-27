package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

public class BaseTest {

    /**
     * Общий отчет для всех тестов
     */
    protected static ExtentReports extent;
    /**
     * Текущий тест в отчете
     */
    protected ExtentTest test;
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;


    @RegisterExtension
    ScreenshotWatcher screenshotWatcher = new ScreenshotWatcher();

    static {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeEach
    void setup() {
        test = extent.createTest(this.getClass().getSimpleName());
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
    }

    /**
     * После теста: закрытие ресурсов и сохранение скриншотов при ошибках
     */
    @AfterEach
    void tearDown() {
        if (ScreenshotWatcher.getLastScreenshotPath() != null) {
            test.addScreenCaptureFromPath(ScreenshotWatcher.getLastScreenshotPath());
        }
//        page.close();
//        context.close();
//        browser.close();
        playwright.close();
        extent.flush();
    }
}