package Tests.SignUp;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Under10DigitPhone extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SignUpWrongOTPTest.class);

    @Test
    public void openSignUpAndVerify() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            WebElement signUpLink = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys("0964"); // Leave email input blank

            // click sign up
            // WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
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
}
