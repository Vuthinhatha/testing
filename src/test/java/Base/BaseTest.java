package Base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        // Lấy đường dẫn tuyệt đối tới file geckodriver.exe trong thư mục resources
        String geckoDriverPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "geckodriver.exe";
        // Thiết lập thuộc tính hệ thống để Selenium biết vị trí của geckodriver
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);

        // Tạo đối tượng FirefoxOptions để cấu hình các tuỳ chọn khi khởi tạo trình
        // duyệt Firefox
        FirefoxOptions options = new FirefoxOptions();
        // Vô hiệu hóa thông báo trên trình duyệt (popup notifications)
        options.addPreference("dom.webnotifications.enabled", false);
        // Tắt tính năng định vị vị trí người dùng
        options.addPreference("geo.enabled", false);
        // Tắt chế độ thử nghiệm yêu cầu định vị
        options.addPreference("geo.prompt.testing", false);
        // Không cho phép luôn đồng ý khi có yêu cầu định vị
        options.addPreference("geo.prompt.testing.allow", false);
        // Cho phép truy cập tài nguyên file nội bộ mà không bị chặn bởi chính sách bảo
        // mật
        options.addPreference("security.fileuri.strict_origin_policy", false);
        // Cho phép tất cả các phương thức trong yêu cầu preflight CORS
        options.addPreference("network.cors_preflight.allow_methods", "*");

        // Chấp nhận chứng chỉ bảo mật không hợp lệ (hữu ích khi test các trang HTTPS
        // nội bộ)
        options.setAcceptInsecureCerts(true);
        // Thiết lập mức độ log của trình điều khiển Firefox (TRACE = ghi log chi tiết
        // nhất)
        options.setLogLevel(FirefoxDriverLogLevel.TRACE);

        // Khởi tạo trình duyệt Firefox với các tuỳ chọn đã cấu hình
        driver = new FirefoxDriver(options);
        // Mở trình duyệt ở chế độ toàn màn hình
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Tăng thời gian chờ lên 30 giây

        driver.get("https://hasaki.vn/");
        // Wait for the search box to be visible (primary test target)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));

        // Try to handle cookie popup, but don't block if it doesn't appear quickly
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement rejectButton = shortWait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='rejectCookies']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", rejectButton);
            System.out.println("Đã từ chối cookie popup.");
        } catch (Exception e) {
            System.out.println("Không thấy hoặc không thể từ chối cookie popup, tiếp tục...");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
