package Tests.ChangePassword;

import Base.BaseTest;
import Utils.ChangePasswordSetUp;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChangePassword extends BaseTest {
        private static final Logger logger = LogManager.getLogger(ChangePassword.class);

        @Test
        public void above32CharNewPassword() {
                String username = "0966265795";
                String password = "Nhatha1112@";
                String newPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                String oldPassword = "Nhatha1112@";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                        "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi nhập mật khẩu mới vượt quá 32 kí tự.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test
        public void blankNewPassword() {
                String username = "0966265795";
                String password = "Nhatha1112@";
                String newPassword = "      ";
                String oldPassword = "Nhatha1112@";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                        String expectedMessage = "Mật khẩu mới không hợp lệ!";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                        "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu cũ.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test
        public void containBlankPassword() {
                String username = "0966265795";
                String password = "      ";
                String newPassword = "abc def";
                String oldPassword = "      ";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                        String expectedMessage = "Mật khẩu mới không hợp lệ!";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                        "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu cũ.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test
        public void equal32CharNewPassword() {
                String username = "0966265795";
                String password = "abc def";
                String newPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                String oldPassword = "abc def";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                By.cssSelector(".title_profile_page")));

                        String actualMessage = errorElement.getText().trim();
                        String expectedMessage = "Thông tin tài khoản";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi nhập mật khẩu cũ trùng với mật khẩu mới.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }
        @Test
        public void equal6CharNewPassword() {
                String username = "0966265795";
                String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                String newPassword = "123456";
                String oldPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                        By.cssSelector(".title_profile_page")));

                        String actualMessage = errorElement.getText().trim();
                        String expectedMessage = "Thông tin tài khoản";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                        "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi nhập mật khẩu cũ trùng với mật khẩu mới.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }



        @Test
        public void invalidNewPassword() {
                String username = "0966265795";
                String password = "123456";
                String newPassword = "abc";
                String oldPassword = "123456";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                        "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu cũ.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test
        public void noFillNewPassword() {
                String username = "0966265795";
                String password = "123456";
                String newPassword = "";
                String oldPassword = "123456";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                        String expectedMessage = "Thông tin bắt buộc.";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                        "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu cũ.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test
        public void noFillOldPassword() {
                String username = "0966265795";
                String password = "123456";
                String newPassword = "123456";
                String oldPassword = "";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                        By.id("password-error")));

                        String actualMessage = errorElement.getText().trim();
                        String expectedMessage = "Thông tin bắt buộc.";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                        "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu cũ.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test
        public void sameOldPassword() {
                String username = "0966265795";
                String password = "123456";
                String newPassword = "123456";
                String oldPassword = "123456";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                        By.cssSelector(".alert.alert-danger")));

                        String actualMessage = errorElement.getText().trim();
                        String expectedMessage = "Mật khẩu cũ và mật khẩu mới không được trùng nhau";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                        "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi nhập mật khẩu cũ trùng với mật khẩu mới.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test
        public void under32CharNewPassword() {
                String username = "0966265795";
                String password = "123456";
                String newPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                String oldPassword = "123456";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                By.cssSelector(".title_profile_page")));

                        String actualMessage = errorElement.getText().trim();
                        String expectedMessage = "Thông tin tài khoản";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi nhập mật khẩu cũ trùng với mật khẩu mới.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test
        public void under6CharNewPassword() {
                String username = "0966265795";
                String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                String newPassword = "aa";
                String oldPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu cũ.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test(priority = 1)
        public void wrongOldPassword() {
                String username = "0966265795";
                String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                String newPassword = "123456";
                String oldPassword = "abc";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                By.cssSelector(".alert.alert-danger")));

                        String actualMessage = errorElement.getText().trim();
                        String expectedMessage = "Mật khẩu hiện tại không đúng";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi nhập mật khẩu cũ không chính xác.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }

        @Test(priority = 2)
        public void validPassword() {
                String username = "0966265795";
                String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                String newPassword = "Nhatha1112@";
                String oldPassword = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

                try {
                        ChangePasswordSetUp.changePassword(driver, username, password);

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                                By.cssSelector(".title_profile_page")));

                        String actualMessage = errorElement.getText().trim();
                        String expectedMessage = "Thông tin tài khoản";

                        Assert.assertEquals(actualMessage, expectedMessage,
                                "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '"
                                        + actualMessage + "'");

                        logger.info("Test passed: Hiển thị đúng thông báo lỗi khi nhập mật khẩu cũ trùng với mật khẩu mới.");

                } catch (Exception e) {
                        Assert.fail("Fail to fill change password form" + e.getMessage());
                }
        }
}
