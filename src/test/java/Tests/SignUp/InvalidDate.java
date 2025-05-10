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

public class InvalidDate extends BaseTest {
    private static final Logger logger = LogManager.getLogger(InvalidDate.class);

    @Test
    public void openSignUpAndVerify() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

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
}
