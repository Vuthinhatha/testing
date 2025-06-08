package Tests.Pay;

import Base.BaseTest;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.LoginSetup;
import Utils.PayFormFill;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Payment extends BaseTest {
        private static final Logger logger = LogManager.getLogger(Payment.class);

        @Test
        public void noFillPhone() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "", "Nguyễn Văn A", "Hồ Chí Minh - Quận 1", "Phường Tân Định",
                                        "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Vui lòng điền số điện thoại')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println("✅ Test passed: 'Vui lòng điền số điện thoại' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void blankPhone() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "                          ", "Nguyễn Văn A",
                                        "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Số điện thoại không hợp lệ')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println("✅ Test passed: 'Số điện thoại không hợp lệ' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void includeBlankPhone() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, " 0966265795 ", "Nguyễn Văn A", "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Số điện thoại hợp lệ')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println("✅ Test passed: 'Số điện thoại không hợp lệ' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void invalidPhone() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "6573928481", "Nguyễn Văn A", "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Số điện thoại không hợp lệ')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println("✅ Test passed: 'Số điện thoại không hợp lệ' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void longPhone() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "098563826482764824", "Nguyễn Văn A", "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Số điện thoại không hợp lệ')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println("✅ Test passed: 'Số điện thoại không hợp lệ' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void shortPhone() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "0964", "Nguyễn Văn A", "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Số điện thoại không hợp lệ')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println("✅ Test passed: 'Số điện thoại không hợp lệ' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void specialCharName() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "0966265795", "Hạ@@@123", "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Họ tên không bao gồm những kí tự đặc biệt và số')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Họ tên không bao gồm những kí tự đặc biệt và số' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void longName() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "0966265795",
                                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                                        "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Họ tên không thể dài hơn 128 ký tự')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Họ tên không thể dài hơn 128 ký tự' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void skipProvince() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "0966265795",
                                        "Nguyễn Văn A",
                                        "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6", true, true, true, true);
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Vui lòng chọn Tỉnh/ TP, Quận/ Huyện')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Vui lòng chọn Tỉnh/ TP, Quận/ Huyện' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void skipDistrict() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "0966265795",
                                        "Nguyễn Văn A",
                                        "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6", false, true, true, true);
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Vui lòng chọn Phường/ Xã')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Vui lòng chọn Phường/ Xã' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void BlankAddress() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "0966265795",
                                        "Nguyễn Văn A",
                                        "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "                 ");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Địa chỉ không thể chứa toàn bộ là khoảng trắng')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Địa chỉ không thể chứa toàn bộ là khoảng trắng' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void LongAddress() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "0966265795",
                                        "Nguyễn Văn A",
                                        "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định",
                                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                                        false, false, false, true);
                        // List<WebElement> buttons = wait.until(ExpectedConditions
                        // .presenceOfAllElementsLocatedBy(By.xpath("//button[contains(text(), 'Tiếp
                        // tục')]")));

                        // if (buttons.size() >= 2) {
                        // WebElement secondButton = buttons.get(1);
                        // ((org.openqa.selenium.JavascriptExecutor)
                        // driver).executeScript("arguments[0].scrollIntoView(true);",
                        // secondButton);
                        // wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        // } else {
                        // throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        // }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//span[contains(text(), 'Địa chỉ không được vượt quá 100 ký tự')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Địa chỉ không được vượt quá 100 ký tự' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void validForm() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "337lvxje@chefalicious.com", "123456");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        // form fill
                        PayFormFill.fillForm(driver, "0966265795",
                                        "Nguyễn Văn A",
                                        "Hồ Chí Minh - Quận 1",
                                        "Phường Tân Định", "4A, Ngõ 6");
                        List<WebElement> buttons = wait.until(ExpectedConditions
                                        .presenceOfAllElementsLocatedBy(
                                                        By.xpath("//button[contains(text(), 'Tiếp tục')]")));

                        if (buttons.size() >= 2) {
                                WebElement secondButton = buttons.get(1);
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                                "arguments[0].scrollIntoView(true);",
                                                secondButton);
                                wait.until(ExpectedConditions.elementToBeClickable(secondButton)).click();
                        } else {
                                throw new RuntimeException("Second 'Tiếp tục' button not found.");
                        }

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//h2[contains(text(), 'Xác thực OTP')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Xác thực OTP' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void purchaseSuccess() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Đặt hàng']")))
                                        .click();

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//div[contains(text(), 'Chọn phương thức thanh toán')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Chọn phương thức thanh toán' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

        @Test
        public void purchaseBlankCart() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");

                        // Open cart
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        // Check if "Tiến hành đặt hàng" button is NOT present
                        boolean isCheckoutButtonPresent = driver
                                        .findElements(By.xpath("//button[text()='Tiến hành đặt hàng']"))
                                        .size() > 0;

                        Assert.assertFalse(isCheckoutButtonPresent,
                                        "❌ 'Tiến hành đặt hàng' button should not be visible for a blank cart!");
                        System.out.println(
                                        "✅ Test passed: 'Tiến hành đặt hàng' button is not present for a blank cart.");

                } catch (Exception e) {
                        logger.error("Blank cart test failed: {}", e.getMessage());
                        Assert.fail("Blank cart test failed: " + e.getMessage());
                }
        }

        @Test
        public void changePaymentMethod() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        wait.until(ExpectedConditions
                                        .elementToBeClickable(By.xpath("//button[text()='Tiến hành đặt hàng']")))
                                        .click();

                        WebElement changePaymentButton = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//section[.//h2[contains(text(), 'Hình thức thanh toán')]]//button[text()='Thay đổi']")));
                        changePaymentButton.click();
                        // Locate the <p> element with the specific text
                        WebElement vietcombankPaymentOption = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//p[text()='Thanh toán trực tuyến Vietcombank']")));

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[@type='button' and contains(text(), 'Tiếp tục')]"))).click();

                        vietcombankPaymentOption.click();

                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Đặt hàng']")))
                                        .click();

                        // After clicking second 'Tiếp tục' button
                        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//h4[contains(text(), 'Thanh toán bằng thẻ ATM')]")));

                        Assert.assertTrue(errorMessage.isDisplayed(),
                                        "Error message for blank phone is not displayed!");
                        System.out.println(
                                        "✅ Test passed: 'Thanh toán bằng thẻ ATM' error message appeared.");

                } catch (Exception e) {
                        logger.error("Payment failed: {}", e.getMessage());
                        Assert.fail("Payment failed: " + e.getMessage());
                }

        }

}
