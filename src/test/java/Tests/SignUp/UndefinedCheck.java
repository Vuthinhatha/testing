package Tests.SignUp;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import Utils.TempMailUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.support.ui.Select;

public class UndefinedCheck extends BaseTest {
    private static final Logger logger = LogManager.getLogger(UndefinedCheck.class);

    @Test
    public void openSignUpAndVerify() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

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
}
