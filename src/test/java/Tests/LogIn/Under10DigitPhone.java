package Tests.LogIn;

import Base.BaseTest;
import Utils.LoginSetup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Under10DigitPhone extends BaseTest {
    private static final Logger logger = LogManager.getLogger(Under10DigitPhone.class);

    @Test
    public void testLoginSuccess() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        String username = "098";
        String password = "Nhatha1112@";

        try {
            LoginSetup.login(driver, username, password);
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại (Exception trong LoginSetup): " + e.getMessage());
        }

        try {
            // Wait for the actual error message div inside .showMessageError
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".showMessageError .alert.alert-danger")));

            String actualMessage = errorElement.getText().trim();
            String expectedMessage = "Tên đăng nhập không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập tên đăng nhập.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

}
