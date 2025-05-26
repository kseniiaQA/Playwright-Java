package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected static ExtentReports extent;
    protected ExtentTest test;
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    public static void beforeAll() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
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

    @AfterEach
    void tearDown() {
        browser.close();
        playwright.close();
        extent.flush();
    }

    @AfterAll
    public static void afterAll() {
        extent.flush();
    }
}

