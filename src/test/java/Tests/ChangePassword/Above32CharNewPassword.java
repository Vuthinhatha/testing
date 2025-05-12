package Tests.ChangePassword;

import Base.BaseTest;
import Tests.LogIn.BlankMail;
import Utils.ChangePasswordSetUp;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Above32CharNewPassword extends BaseTest {
    private static final Logger logger = LogManager.getLogger(Above32CharNewPassword.class);

    @Test
    public void above32CharNewPassword() {
        String username = "0966265795";
        String password = "Nhatha1112@";
        String newPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String oldPassword = "Nhatha1112@";

        try {
        ChangePasswordSetUp.changePassword(driver, username, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            WebElement oldPasswordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("password")));
            oldPasswordInput.sendKeys(oldPassword);

            WebElement newPasswordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("passwordNew")));
            newPasswordInput.sendKeys(newPassword);

            WebElement confirmPasswordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("passwordConfirm")));
            confirmPasswordInput.sendKeys(newPassword);


            WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space(text())='Cập nhật']")));
            updateButton.click();
            

            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("passwordNew-error")));

            String actualMessage = errorElement.getText().trim();
            String expectedMessage = "Vui lòng nhập mật khẩu có độ dài từ 6 đến 32 kí tự";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi nhập mật khẩu mới vượt quá 32 kí tự.");

        } catch (Exception e) {
            Assert.fail("Fail to fill change password form" + e.getMessage());
        }
    }
}
