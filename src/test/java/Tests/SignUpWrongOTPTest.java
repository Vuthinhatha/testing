package Tests;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import Utils.TempMailUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.support.ui.Select;

public class SignUpWrongOTPTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SignUpWrongOTPTest.class);

    @Test
    public void openSignUpAndVerify() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

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

}
