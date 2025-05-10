package Tests.SignUp;

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

public class BlankCode extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SignUpWrongOTPTest.class);

    @Test
    public void openSignUpAndVerify() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

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

            //click sign up
            WebElement signUp= wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            // Wait for the error message to appear
            // WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            //         By.id("otp-error")));

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
}
