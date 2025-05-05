import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumTest {
    public static ChromeOptions options ;
    public static WebDriver driver ;
    @BeforeTest
    public static void Setup(){
        options = new ChromeOptions();
        // Tắt popup notifications
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origin=*");//Giai quyet lỗi liên quan đến chính sach CORS khi chạy automation.

        // dẫn đến chromedriver
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://hasaki.vn/");

    }
    @Test
    void teststeps(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Chấp nhận cookies nếu popup hiện
        try {
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("acceptCookies")));
            cookieBtn.click();
        } catch (Exception e) {
            System.out.println("Không thấy popup cookies");
        }

        // Nhấn nút "Đăng nhập / Đăng ký"
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-login"))).click();

        // Nhập email/số điện thoại
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Nhập email hoặc số điện thoại']")));
        emailInput.sendKeys("0966265795"); // ⚠ Thay bằng số thật

        // Nhập password
        WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Nhập password']"));
        passwordInput.sendKeys("Nhatha1112@"); // ⚠ Thay bằng mật khẩu thật

        // Click nút "Đăng nhập"
        WebElement loginBtn = driver.findElement(By.xpath("//button[text()='Đăng nhập']"));
        loginBtn.click();
    }

}
