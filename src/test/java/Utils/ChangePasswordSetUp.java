package Utils;

import Base.BaseTest;
import Utils.LoginSetup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ChangePasswordSetUp {
    public static void changePassword(WebDriver driver, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Đăng nhập
        try {
            LoginSetup.login(driver, username, password);
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại: " + e.getMessage());
        }

        // Xác nhận đăng nhập thành công bằng sự hiện diện của liên kết "Tài khoản"
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(), 'Tài khoản')]")));
            System.out.println("Đăng nhập thành công! Phần tử 'Tài khoản' được tìm thấy.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy phần tử 'Tài khoản'. Đăng nhập không thành công: " + e.getMessage());
        }

        // Hover vào icon người dùng (div cha)
        try {
            WebElement accountMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.item_header.item_login.user_login")));

            Actions actions = new Actions(driver);
            actions.moveToElement(accountMenu).perform();

            System.out.println("Di chuột thành công vào phần tử tài khoản.");

            // Chờ hiển thị dropdown và click vào "Tài khoản của bạn"
            WebElement yourAccountLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Tài khoản của bạn')]")));

            yourAccountLink.click();

            System.out.println("Đã nhấn vào liên kết 'Tài khoản của bạn'.");

        } catch (Exception e) {
            Assert.fail("Không thể hover hoặc click vào 'Tài khoản của bạn': " + e.getMessage());
        }

        // Click "Chỉnh sửa"
        try {
            WebElement editLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Chỉnh sửa')]")));
            editLink.click();
            System.out.println("Đã nhấn vào liên kết 'Chỉnh sửa'.");
        } catch (Exception e) {
            Assert.fail("Không thể tìm hoặc nhấn vào liên kết 'Chỉnh sửa': " + e.getMessage());
        }
        // ✅ Step 5: Click "Cập nhật" to go to password change
        try {
            WebElement updatePasswordLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Cập nhật') and contains(@href,'/customer/password/change')]")));
            updatePasswordLink.click();
            System.out.println("Đã nhấn vào liên kết 'Cập nhật' để đổi mật khẩu.");
        } catch (Exception e) {
            Assert.fail("Không thể nhấn vào liên kết 'Cập nhật': " + e.getMessage());
        }
    }
}
