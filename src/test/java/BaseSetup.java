import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.time.Duration;

public class BaseSetup {

    protected static FirefoxOptions options;
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeTest
    public static void Setup() {
        String geckoDriverPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "geckodriver.exe";
        System.out.println("Geckodriver path: " + geckoDriverPath);
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);

        options = new FirefoxOptions();
        // Tắt thông báo (notifications) của trang web
        options.addPreference("dom.webnotifications.enabled", false);
        // Tắt yêu cầu truy cập vị trí
        options.addPreference("geo.enabled", false);
        options.addPreference("geo.prompt.testing", false);
        options.addPreference("geo.prompt.testing.allow", false);
        // Nới lỏng kiểm tra CORS (chỉ dùng trong kiểm thử)
        options.addPreference("security.fileuri.strict_origin_policy", false);
        options.addPreference("network.cors_preflight.allow_methods", "*");

        // Các thiết lập khác
        options.setAcceptInsecureCerts(true);
        options.setLogLevel(FirefoxDriverLogLevel.TRACE);

        try {
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Getter để các lớp khác truy cập driver và wait
    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }
}
