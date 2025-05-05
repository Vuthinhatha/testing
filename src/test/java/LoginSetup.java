//package test; // (hoặc package nào bạn đang dùng)

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSetup {

    public static void login(WebDriver driver, String username, String password) {
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
                By.xpath("//*[@id=\"username\"]")));
        emailInput.sendKeys(username);

        // Nhập password
        WebElement passwordInput = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordInput.sendKeys(password);

        // Click nút "Đăng nhập"
        WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"form-head-login\"]/button"));
        loginBtn.click();
    }
}
