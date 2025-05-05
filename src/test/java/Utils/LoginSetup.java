package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class LoginSetup {
    public static void login(WebDriver driver, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Nhấn nút "Đăng nhập / Đăng ký"
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-login"))).click();
            System.out.println("Đã nhấp nút 'Đăng nhập / Đăng ký'.");
        } catch (Exception e) {
            System.out.println("Không thể nhấp nút 'Đăng nhập / Đăng ký': " + e.getMessage());
        }

        // Nhập email/số điện thoại
        try {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"username\"]")));
            emailInput.sendKeys(username);
            System.out.println("Đã nhập email/số điện thoại: " + username);
        } catch (Exception e) {
            System.out.println("Không thể nhập email/số điện thoại: " + e.getMessage());
        }

        // Nhập password
        try {
            WebElement passwordInput = driver.findElement(By.xpath("//*[@id=\"password\"]"));
            passwordInput.sendKeys(password);
            System.out.println("Đã nhập mật khẩu.");
        } catch (Exception e) {
            System.out.println("Không thể nhập mật khẩu: " + e.getMessage());
        }

        // Click nút "Đăng nhập"
        try {
            WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"form-head-login\"]/button"));
            loginBtn.click();
            System.out.println("Đã nhấp nút 'Đăng nhập'.");
        } catch (Exception e) {
            System.out.println("Không thể nhấp nút 'Đăng nhập': " + e.getMessage());
        }
    }
}
