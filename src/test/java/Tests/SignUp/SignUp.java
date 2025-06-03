package Tests.SignUp;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
// import java.util.List;
import java.util.List;

import Utils.TempMailUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.support.ui.Select;

public class SignUp extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SignUp.class);

    @Test
    public void legitDate() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Select day
            WebElement dayElement = driver.findElement(By.id("reg-day"));
            Select daySelect = new Select(dayElement);
            daySelect.selectByVisibleText("15");
            String selectedDay = daySelect.getFirstSelectedOption().getText();
            Assert.assertEquals(selectedDay, "15", "Day should be 15");

            // Select month
            WebElement monthElement = driver.findElement(By.id("reg-month"));
            Select monthSelect = new Select(monthElement);
            monthSelect.selectByVisibleText("8");
            String selectedMonth = monthSelect.getFirstSelectedOption().getText();
            Assert.assertEquals(selectedMonth, "8", "Month should be 8");

            // Select year
            WebElement yearElement = driver.findElement(By.id("reg-year"));
            Select yearSelect = new Select(yearElement);
            yearSelect.selectByVisibleText("2000");
            String selectedYear = yearSelect.getFirstSelectedOption().getText();
            Assert.assertEquals(selectedYear, "2000", "Year should be 2000");

            logger.info("Date selection verified: 15/8/2000");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void legitName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "Vũ Hạ";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void longPassword() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("123456");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));
            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Mật khẩu phải từ 6 đến 32 ký tự !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void maleCheck() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Select the "Nam" radio button (value="0")
            WebElement maleRadio = driver.findElement(By.cssSelector("input[name='gender'][value='1']"));
            maleRadio.click();

            // Get all gender radio buttons
            List<WebElement> genderOptions = driver.findElements(By.cssSelector("input[name='gender']"));

            // Assert the correct one is selected and others are not
            for (WebElement option : genderOptions) {
                String value = option.getAttribute("value");
                if (value.equals("1")) {
                    Assert.assertTrue(option.isSelected(), "'Nam' radio button should be selected.");
                } else {
                    Assert.assertFalse(option.isSelected(),
                            "Radio button with value '" + value + "' should not be selected.");
                }
            }

            logger.info("Test passed: 'Nam' is selected, others are not.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void femaleCheck() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Select the "Nu" radio button (value="0")
            WebElement maleRadio = driver.findElement(By.cssSelector("input[name='gender'][value='2']"));
            maleRadio.click();

            // Get all gender radio buttons
            List<WebElement> genderOptions = driver.findElements(By.cssSelector("input[name='gender']"));

            // Assert the correct one is selected and others are not
            for (WebElement option : genderOptions) {
                String value = option.getAttribute("value");
                if (value.equals("2")) {
                    Assert.assertTrue(option.isSelected(), "'Nu' radio button should be selected.");
                } else {
                    Assert.assertFalse(option.isSelected(),
                            "Radio button with value '" + value + "' should not be selected.");
                }
            }

            logger.info("Test passed: 'Nu' is selected, others are not.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noFillCode() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265795");

            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("otp-error")));

            // Validate the error message text
            String expectedText = "Nhập mã xác thực 6 số !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noFillDate() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noFillName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "abc123";
            String fullname = "";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("123456");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));
            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Vui lòng nhập họ tên !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noFillPassword() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("123456");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys("");
            logger.info("Filled password: {}", "");

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));
            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Vui lòng nhập mật khẩu !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void over10DigitPhone() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("098563826482764824"); // Leave email input blank

            // click sign up
            // WebElement signUp =
            // wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            // signUp.click();

            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();
            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".showRegisterMessage"))); // Adjust selector if needed

            // Validate the error message text
            String expectedText = "Tên đăng nhập không tồn tại hoặc không đúng định dạng.";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void over64CharMail() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("hdfghfhfgdhdfghfdghfdghhhhhhhdfghsthstbthjrtjrtjtrjrtjddddddddddddddddd@gmail.com"); // Leave
                                                                                                                      // email
                                                                                                                      // input
                                                                                                                      // blank

            // click sign up
            // WebElement signUp =
            // wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            // signUp.click();

            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();
            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".showRegisterMessage"))); // Adjust selector if needed

            // Validate the error message text
            String expectedText = "Tên đăng nhập không tồn tại hoặc không đúng định dạng.";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void shortPassword() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "abc";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("123456");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));
            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Mật khẩu phải từ 6 đến 32 ký tự !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void above6DigitCode() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("85748374");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.id("otp-error")));
            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Mã xác thực hết hạn hoặc không đúng.";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void above255CharName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.id("email-error")));
            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Đăng ký không thành công, vui lòng thử lại sau.";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void blankCode() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265795");

            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("      ");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.id("otp-error")));

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Vui lòng nhập mã sms!";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void blankName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            try {
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("email-error")));
                String expectedText = "Vui lòng nhập họ tên !";
                String actualText = errorMessage.getText().trim();

                Assert.assertEquals(actualText, expectedText,
                        "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

                logger.info("Test passed: Correct error message displayed.");
            } catch (TimeoutException e) {
                Assert.fail("Expected error message element with ID 'email-error' was not visible within timeout.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void blankPassword() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "abc 123456";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("123456");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));
            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Mật khẩu không đúng định dạng";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void codeContainBlank() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code + " ");

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void containBlankName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "Vũ Hạ";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void correctPassword() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "Nhatha1112@";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equal6DigitCode() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equal6LengthPassword() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "abc123";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equal10DigitPhone() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265795"); // Leave email input blank

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("otp-error")));

            // Validate the error message text
            String expectedText = "Nhập mã xác thực 6 số !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void equal64CharMail() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("hdfghfhfgdhdfghfdghfdghhhhhhhdfghsthstbthjrtjrtjtrjrj@gmail.com"); // Leave email input
                                                                                                    // blank

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("otp-error")));

            // Validate the error message text
            String expectedText = "Nhập mã xác thực 6 số !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void equal255CharName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void from6to32Password() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "aaaaaaaaaaaaaaaaa";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidDate() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Select day
            WebElement dayElement = driver.findElement(By.id("reg-day"));
            Select daySelect = new Select(dayElement);
            daySelect.selectByVisibleText("31");
            String selectedDay = daySelect.getFirstSelectedOption().getText();
            Assert.assertEquals(selectedDay, "31", "Day should be 31");

            // Select month
            WebElement monthElement = driver.findElement(By.id("reg-month"));
            Select monthSelect = new Select(monthElement);
            monthSelect.selectByVisibleText("2");
            String selectedMonth = monthSelect.getFirstSelectedOption().getText();
            Assert.assertEquals(selectedMonth, "2", "Month should be 2");

            // Select year
            WebElement yearElement = driver.findElement(By.id("reg-year"));
            Select yearSelect = new Select(yearElement);
            yearSelect.selectByVisibleText("2000");
            String selectedYear = yearSelect.getFirstSelectedOption().getText();
            Assert.assertEquals(selectedYear, "2000", "Year should be 2000");

            logger.info("Date selection verified: 31/2/2000");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void blankMailInput() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(""); // Leave email input blank

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));

            // Validate the error message text
            String expectedText = "Vui lòng nhập email hoặc số điện thoại !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void invalidMailInput() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("Vuha#$gmail.com"); // Leave email input blank

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));

            // Validate the error message text
            String expectedText = "Email hoặc số điện thoại không đúng định dạng !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void invalidPhoneInput() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("6573928481"); // Leave email input blank

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));

            // Validate the error message text
            String expectedText = "Email hoặc số điện thoại không đúng định dạng !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void spaceAroundMailInput() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(" havy79276@gmail.com "); // Leave email input blank

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));

            // Validate the error message text
            String expectedText = "Email hoặc số điện thoại không đúng định dạng !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void spaceMailInput() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("                          "); // Leave email input blank

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));

            // Validate the error message text
            String expectedText = "Email hoặc số điện thoại không đúng định dạng !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void wrongOTP() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            // Thread.sleep(3000);
            // WebElement getCodeButton = driver.findElement(By.id("verifyUserName"));
            // getCodeButton.click();

            // Step 4: Wait for email and extract code
            // Thread.sleep(10000); // wait for email to arrive
            // String messageId = TempMailUtils.waitForMessageId(token);
            // String messageText = TempMailUtils.getMessageText(token, messageId);
            // String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";
            String fakeOTP = "123456";

            // Step 5: Fill verification code fake
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(fakeOTP);

            logger.info("Filled verification code: {}", fakeOTP);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);

            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Mã xác thực hết hạn hoặc không đúng.";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void specialCharCode() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("957@@");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.id("otp-error")));
            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Mã xác thực hết hạn hoặc không đúng.";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void specialCharName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "abc123";
            String fullname = "@#$%&*()_+";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("123456");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            try {
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("email-error")));
                String expectedText = "Họ tên sai định dạng";
                String actualText = errorMessage.getText().trim();

                Assert.assertEquals(actualText, expectedText,
                        "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

                logger.info("Test passed: Correct error message displayed.");
            } catch (TimeoutException e) {
                Assert.fail("Expected error message element with ID 'email-error' was not visible within timeout.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void true32LongPassword() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void undefinedCheck() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Select the "Khong xac dinh" radio button (value="0")
            WebElement maleRadio = driver.findElement(By.cssSelector("input[name='gender'][value='0']"));
            maleRadio.click();

            // Get all gender radio buttons
            List<WebElement> genderOptions = driver.findElements(By.cssSelector("input[name='gender']"));

            // Assert the correct one is selected and others are not
            for (WebElement option : genderOptions) {
                String value = option.getAttribute("value");
                if (value.equals("0")) {
                    Assert.assertTrue(option.isSelected(), "'Khong xac dinh' radio button should be selected.");
                } else {
                    Assert.assertFalse(option.isSelected(),
                            "Radio button with value '" + value + "' should not be selected.");
                }
            }

            logger.info("Test passed: 'Khong xac dinh' is selected, others are not.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void under6DigitCode() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("75");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.id("otp-error")));
            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Mã xác thực hết hạn hoặc không đúng.";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void under10DigitPhone() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0964"); // Leave email input blank

            // click sign up
            // WebElement signUp =
            // wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            // signUp.click();

            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();
            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".showRegisterMessage"))); // Adjust selector if needed

            // Validate the error message text
            String expectedText = "Tên đăng nhập không tồn tại hoặc không đúng định dạng.";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void under64CharMail() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("hdfghfhfgdhdfghfdghfdghhhhhhhdfghsthstbthjrtjrtjtrj@gmail.com"); // Leave email input
                                                                                                  // blank

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("otp-error")));

            // Validate the error message text
            String expectedText = "Nhập mã xác thực 6 số !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            logger.error("Test failed due to an exception: ", e);

        }
    }

    @Test
    public void under255CharName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Create temp email
            String tempEmail = TempMailUtils.generateRandomEmail();
            String password = "12345";
            TempMailUtils.createAccount(tempEmail, password);
            String token = TempMailUtils.getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            Thread.sleep(3000);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = TempMailUtils.waitForMessageId(token);
            if (messageId == null || messageId.trim().equalsIgnoreCase("No email message")) {
                Assert.fail("Failed to receive verification email. messageId: " + messageId);
            }

            String messageText = TempMailUtils.getMessageText(token, messageId);
            String code = TempMailUtils.extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "aa";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            logger.info("Filled verification code: {}", code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.popup-register")));

            Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");

            logger.info("Test passed: '.popup-register' is not visible as expected.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void usedPhone() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265795");

            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("957@@");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.id("otp-error")));
            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Email hoặc số điện thoại đã được đăng ký trước đó. Vui lòng kiểm tra lại";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void veryShortPassword() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0966265796");

            String passwordString = "aa";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys("123456");

            logger.info("Filled verification code: {}", "");

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-password")));
            passwordInput.sendKeys(passwordString);
            logger.info("Filled password: {}", passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-fullname")));
            fullnameInput.sendKeys(fullname);
            logger.info("Filled fullname: {}", fullname);
            // Select day
            WebElement daySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-day")));
            Select daySelect = new Select(daySelectElement);
            daySelect.selectByVisibleText("15"); // select 15th day

            // Select month
            WebElement monthSelectElement = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-month")));
            Select monthSelect = new Select(monthSelectElement);
            monthSelect.selectByValue("5"); // May (value="5")

            // Select year
            WebElement yearSelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg-year")));
            Select yearSelect = new Select(yearSelectElement);
            yearSelect.selectByVisibleText("1995"); // select 1995

            // click sign up
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("email-error")));
            // Wait for the error message to appear
            // WebElement errorMessage =
            // wait.until(ExpectedConditions.visibilityOfElementLocated(
            // By.cssSelector("div.showRegisterMessage.text-danger.alert.alert-danger")));

            // Validate the error message text
            String expectedText = "Mật khẩu phải từ 6 đến 32 ký tự !";
            String actualText = errorMessage.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Expected error message: \"" + expectedText + "\", but got: \"" + actualText + "\"");

            logger.info("Test passed: Correct error message displayed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
