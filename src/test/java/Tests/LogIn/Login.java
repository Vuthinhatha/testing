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

public class Login extends BaseTest {
    private static final Logger logger = LogManager.getLogger(Login.class);

    @Test
    public void above10DigitPhone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "098563826482764824";
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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập tên đăng nhập.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void above32CharPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "0966265795";
        String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void above64CharMail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com";
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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập tên đăng nhập.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void blankMail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "       ";
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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập tên đăng nhập.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void blankPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "0966265795";
        String password = "       ";

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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void containBlankPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "0966265795";
        String password = "abc def";

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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void equal6Char() {
        logger.info("Bắt đầu test đăng nhập thành công");
        // Khởi tạo WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String username = "0966265795";
        String password = "123456";
        // Gọi hàm login từ LoginSetup
        try {
            LoginSetup.login(driver, username, password); // Thay bằng email và mật khẩu thực tế
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại: " + e.getMessage());
        }

        // Kiểm tra đăng nhập thành công
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(), 'Tài khoản')]"))); // Selector cho phần tử chỉ xuất hiện sau khi đăng
                                                                      // nhập
            System.out.println("Đăng nhập thành công! Phần tử 'Tài khoản' được tìm thấy.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy phần tử 'Tài khoản'. Đăng nhập không thành công: " + e.getMessage());
        }

        // Kiểm tra tiêu đề trang (tùy chọn)
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Tiêu đề trang không được null!");
        Assert.assertTrue(pageTitle.contains("Hasaki"), "Tiêu đề trang không chứa từ 'Hasaki'!");
    }

    @Test
    public void equal10DigitPhone() {
        logger.info("Bắt đầu test đăng nhập thành công");
        // Khởi tạo WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String username = "0966265795";
        String password = "Nhatha1112@";
        // Gọi hàm login từ LoginSetup
        try {
            LoginSetup.login(driver, username, password); // Thay bằng email và mật khẩu thực tế
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại: " + e.getMessage());
        }

        // Kiểm tra đăng nhập thành công
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(), 'Tài khoản')]"))); // Selector cho phần tử chỉ xuất hiện sau khi đăng
                                                                      // nhập
            System.out.println("Đăng nhập thành công! Phần tử 'Tài khoản' được tìm thấy.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy phần tử 'Tài khoản'. Đăng nhập không thành công: " + e.getMessage());
        }

        // Kiểm tra tiêu đề trang (tùy chọn)
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Tiêu đề trang không được null!");
        Assert.assertTrue(pageTitle.contains("Hasaki"), "Tiêu đề trang không chứa từ 'Hasaki'!");
    }

    @Test
    public void equal32CharPassword() {
        logger.info("Bắt đầu test đăng nhập thành công");
        // Khởi tạo WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String username = "0966265795";
        String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        // Gọi hàm login từ LoginSetup
        try {
            LoginSetup.login(driver, username, password); // Thay bằng email và mật khẩu thực tế
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại: " + e.getMessage());
        }

        // Kiểm tra đăng nhập thành công
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(), 'Tài khoản')]"))); // Selector cho phần tử chỉ xuất hiện sau khi đăng
                                                                      // nhập
            System.out.println("Đăng nhập thành công! Phần tử 'Tài khoản' được tìm thấy.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy phần tử 'Tài khoản'. Đăng nhập không thành công: " + e.getMessage());
        }

        // Kiểm tra tiêu đề trang (tùy chọn)
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Tiêu đề trang không được null!");
        Assert.assertTrue(pageTitle.contains("Hasaki"), "Tiêu đề trang không chứa từ 'Hasaki'!");
    }

    @Test
    public void equal64CharMail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com";
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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập tên đăng nhập.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void legitLogin() {
        logger.info("Bắt đầu test đăng nhập thành công");
        // Khởi tạo WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String username = "0966265795";
        String password = "Nhatha1112@";
        // Gọi hàm login từ LoginSetup
        try {
            LoginSetup.login(driver, username, password); // Thay bằng email và mật khẩu thực tế
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại: " + e.getMessage());
        }

        // Kiểm tra đăng nhập thành công
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(), 'Tài khoản')]"))); // Selector cho phần tử chỉ xuất hiện sau khi đăng
                                                                      // nhập
            System.out.println("Đăng nhập thành công! Phần tử 'Tài khoản' được tìm thấy.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy phần tử 'Tài khoản'. Đăng nhập không thành công: " + e.getMessage());
        }

        // Kiểm tra tiêu đề trang (tùy chọn)
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Tiêu đề trang không được null!");
        Assert.assertTrue(pageTitle.contains("Hasaki"), "Tiêu đề trang không chứa từ 'Hasaki'!");
    }

    @Test
    public void mailContainBlank() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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

    @Test
    public void noFillBoth() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "";
        String password = "";

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
            String expectedMessage = "Vui lòng nhập tên đăng nhập";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập tên đăng nhập.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Vui lòng nhập tên đăng nhập': " + e.getMessage());
        }
    }

    @Test
    public void noFillPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "0966265795";
        String password = "";

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
            String expectedMessage = "Vui lòng nhập mật khẩu";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void under6CharPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "0966265795";
        String password = "abc";

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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void under10DigitPhone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập tên đăng nhập.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void under32CharPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "0966265795";
        String password = "aaaaaaaaaaaaaaaaaaaaaaaaaaaa";

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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập mật khẩu.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

    @Test
    public void under64Mail() {
        logger.info("Bắt đầu test đăng nhập thành công");
        // Khởi tạo WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String username = "0966265795";
        String password = "Nhatha1112@";
        // Gọi hàm login từ LoginSetup
        try {
            LoginSetup.login(driver, username, password); // Thay bằng email và mật khẩu thực tế
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại: " + e.getMessage());
        }

        // Kiểm tra đăng nhập thành công
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(), 'Tài khoản')]"))); // Selector cho phần tử chỉ xuất hiện sau khi đăng
                                                                      // nhập
            System.out.println("Đăng nhập thành công! Phần tử 'Tài khoản' được tìm thấy.");
        } catch (Exception e) {
            Assert.fail("Không tìm thấy phần tử 'Tài khoản'. Đăng nhập không thành công: " + e.getMessage());
        }

        // Kiểm tra tiêu đề trang (tùy chọn)
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Tiêu đề trang không được null!");
        Assert.assertTrue(pageTitle.contains("Hasaki"), "Tiêu đề trang không chứa từ 'Hasaki'!");
    }

    @Test
    public void unmatchedMail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String username = "Vuha#$gmail.com";
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
            String expectedMessage = "Tên đăng nhập hoặc mật khẩu không khớp !";

            Assert.assertEquals(actualMessage, expectedMessage,
                    "Thông báo lỗi không đúng. Mong đợi: '" + expectedMessage + "', Thực tế: '" + actualMessage + "'");

            logger.info("Test passed: Hiển thị đúng thông báo lỗi khi không nhập tên đăng nhập.");

        } catch (Exception e) {
            Assert.fail("Không tìm thấy thông báo lỗi 'Tên đăng nhập không khớp !': " + e.getMessage());
        }
    }

}
