package Tests;


import Base.BaseTest;
import Utils.LoginSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        // Khởi tạo WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Gọi hàm login từ LoginSetup
        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@"); // Thay bằng email và mật khẩu thực tế
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại: " + e.getMessage());
        }

        // Kiểm tra đăng nhập thành công
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(), 'Tài khoản')]"))); // Selector cho phần tử chỉ xuất hiện sau khi đăng nhập
            System.out.println("Đăng nhập thành công! Phần tử 'Tài khoản' được tìm thấy.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy phần tử 'Tài khoản'. Đăng nhập không thành công: " + e.getMessage());
        }

        // Kiểm tra tiêu đề trang (tùy chọn)
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Tiêu đề trang không được null!");
        Assert.assertTrue(pageTitle.contains("Hasaki"), "Tiêu đề trang không chứa từ 'Hasaki'!");
    }
}
