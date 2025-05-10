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

public class NoFillDate extends BaseTest {
    private static final Logger logger = LogManager.getLogger(NoFillDate.class);

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

            //click sign up
            WebElement signUp= wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRegister")));
            signUp.click();

            boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("a.popup-register")));
        
        Assert.assertTrue(isInvisible, "Element '.popup-register' should not be visible.");
        
        logger.info("Test passed: '.popup-register' is not visible as expected.");
        

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
