package base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.nio.file.Paths;

public class BaseTest {


    Playwright playwright;
    Browser browser;
    BrowserContext context;
    public  Page page;


    @BeforeEach
    void createPage() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("videos/")));
        page = context.newPage();
    }

//    @AfterAll
//    static void teardown() {
//        browser.close();
//        playwright.close();
//        // Генерация отчета после всех тестов
//        HtmlReportGenerator.generateReport(
//                CustomReportExtension.getResults(),
//                "test-report.html"
//        );
//    }


    public Page getPage() {
        return page;
    }
}