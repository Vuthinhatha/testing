package Tests.LogIn;

import Base.BaseTest;
import Utils.LoginSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class MailContainBlank extends BaseTest {
    private static final Logger logger = LogManager.getLogger(MailContainBlank.class);

    @Test
    public void testLoginFail_MailContainBlank() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Chuỗi có khoảng trắng dư thừa (nên gây lỗi đăng nhập)
        String username = " 0966265795 ";
        String password = "Nhatha1112@";

        try {
            LoginSetup.login(driver, username, password);

            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".showMessageError .alert.alert-danger")));

            String actualMessage = errorElement.getText().trim();
            String expectedMessage = "Tên đăng nhập không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hệ thống hiển thị đúng lỗi khi tên đăng nhập có khoảng trắng.");

        } catch (Exception e) {
            logger.error("Test failed: Không tìm thấy hoặc không đúng thông báo lỗi mong đợi.", e);
            Assert.fail("Không tìm thấy hoặc không đúng thông báo lỗi mong đợi: " + e.getMessage());
        }
    }
}
