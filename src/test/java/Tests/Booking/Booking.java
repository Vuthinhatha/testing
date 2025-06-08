package Tests.Booking;

import Base.BaseTest;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.LoginSetup;
import Utils.SearchUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Booking extends BaseTest {
    private static final Logger logger = LogManager.getLogger(Booking.class);

    @Test
    public void noFillPhone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and clear it
            WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
            phoneInput.clear(); // or use JS if needed

            // Optionally assert it's empty
            Assert.assertEquals(phoneInput.getAttribute("value"), "", "Phone input is not cleared!");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Vui lòng nhập số điện thoại !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void blankPhone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and clear it
            WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
            phoneInput.clear(); // or use JS if needed
            phoneInput.sendKeys("                          ");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Vui lòng nhập số điện thoại !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void containBlankPhone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and clear it
            WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
            phoneInput.clear(); // or use JS if needed
            phoneInput.sendKeys(" 0966265795 ");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Số điện thoại không hợp lệ !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void invalidPhone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and clear it
            WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
            phoneInput.clear(); // or use JS if needed
            phoneInput.sendKeys("hgyryryg85");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Số điện thoại không hợp lệ !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void phoneNotAllowMoreThan10Digits() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and enter 15-digit number
            WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
            phoneInput.clear();
            phoneInput.sendKeys("123456789012345");

            // Get the value after typing
            String enteredValue = phoneInput.getAttribute("value");
            System.out.println("Value in phone input: " + enteredValue);

            // Assert it only allows 10 digits
            Assert.assertEquals(enteredValue.length(), 10, "Phone input accepts more than 10 digits!");
            Assert.assertEquals(enteredValue, "1234567890", "Phone input value is incorrect!");

            System.out.println("✅ Test passed: Phone input is restricted to 10 digits.");

        } catch (Exception e) {
            logger.error("Phone length test failed: {}", e.getMessage());
            Assert.fail("Phone length test failed: " + e.getMessage());
        }
    }

    @Test
    public void under10DigitPhone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and clear it
            WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone")));
            phoneInput.clear(); // or use JS if needed
            phoneInput.sendKeys("0964");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Số điện thoại không hợp lệ !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void noFillName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and clear it
            WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fullName")));
            nameInput.clear(); // or use JS if needed

            // Optionally assert it's empty
            Assert.assertEquals(nameInput.getAttribute("value"), "", "Name input is not cleared!");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Vui lòng nhập họ tên !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void blankName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and clear it
            WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fullName")));
            nameInput.clear(); // or use JS if needed
            nameInput.sendKeys("                          ");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Vui lòng nhập họ tên !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void specialCharacterName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and clear it
            WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fullName")));
            nameInput.clear(); // or use JS if needed
            nameInput.sendKeys("Hạ@@@123");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Tên không được chứa kí tự đặc biệt !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void nameNotAllowMoreThan50Characters() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Wait for phone input and enter 15-digit number
            WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fullName")));
            nameInput.clear();
            nameInput.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

            // Get the value after typing
            String enteredValue = nameInput.getAttribute("value");
            System.out.println("Value in name input: " + enteredValue);

            // Assert it only allows 10 digits
            Assert.assertEquals(enteredValue.length(), 50, "Name input accepts more than 50 characters!");
            Assert.assertEquals(enteredValue,
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    "Name input value is incorrect!");

            System.out.println("✅ Test passed: Name input is restricted to 50 characters.");

        } catch (Exception e) {
            logger.error("Name length test failed: {}", e.getMessage());
            Assert.fail("Name length test failed: " + e.getMessage());
        }
    }

    @Test
    public void noSelectLocale() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Vui lòng chọn chi nhánh !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void noFillService() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            WebElement secondSlide = driver.findElement(By.xpath("//ul[@class='slides']/li[2]"));
            secondSlide.click();

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Vui lòng nhập dịch vụ bạn muốn làm !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void blankService() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            WebElement secondSlide = driver.findElement(By.xpath("//ul[@class='slides']/li[2]"));
            secondSlide.click();

            // Wait for service input and clear it
            WebElement serviceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("service_note")));
            serviceInput.sendKeys("                          ");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Vui lòng nhập dịch vụ bạn muốn làm !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    // fail
    @Test
    public void specialCharacterService() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            WebElement secondSlide = driver.findElement(By.xpath("//ul[@class='slides']/li[2]"));
            secondSlide.click();

            // Wait for service input and clear it
            WebElement serviceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("service_note")));
            serviceInput.sendKeys("@@@123");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Dịch vụ bạn muốn làm không được chứa kí tự đặc biệt !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }


    @Test
    public void noSelectDate() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            WebElement secondSlide = driver.findElement(By.xpath("//ul[@class='slides']/li[2]"));
            secondSlide.click();

            // Wait for service input and clear it
            WebElement serviceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("service_note")));
            serviceInput.sendKeys("chăm sóc da");

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Vui lòng chọn thời gian đặt hẹn !')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

    @Test
    public void validBooking() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'Đặt Hẹn')]")))
                    .click();

            WebElement secondSlide = driver.findElement(By.xpath("//ul[@class='slides']/li[2]"));
            secondSlide.click();

            // Wait for service input and clear it
            WebElement serviceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("service_note")));
            serviceInput.sendKeys("chăm sóc da");

            Thread.sleep(1000);

            // Step 1: Find the second <ul class="slides">
            List<WebElement> ulList = driver.findElements(By.xpath("//ul[@class='slides']"));
            if (ulList.size() < 2) {
                throw new RuntimeException("Second <ul class='slides'> not found.");
            }
            WebElement targetUl = ulList.get(1);

            // Step 2: Get all <li> inside the second <ul>
            List<WebElement> liList = targetUl.findElements(By.tagName("li"));

            boolean foundSelectableTime = false;

            for (int i = 0; i < liList.size(); i++) {
                WebElement li = liList.get(i);

                // Scroll into view & click the <li>
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", li);
                wait.until(ExpectedConditions.elementToBeClickable(li)).click();
                Thread.sleep(500); // optional: wait for DOM update

                // Step 3: Check div.list_hours.width_common > div elements
                List<WebElement> timeDivs = wait.until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(By.cssSelector("div.list_hours.width_common > div")));

                // Step 4: Find the first div without class 'hetcho'
                for (WebElement timeDiv : timeDivs) {
                    String classAttr = timeDiv.getAttribute("class");
                    if (!classAttr.contains("hetcho")) {
                        timeDiv.click(); // Click the selectable time
                        foundSelectableTime = true;
                        break;
                    }
                }

                if (foundSelectableTime) {
                    System.out.println("✅ Found and selected a valid time.");
                    break;
                }
            }

            if (!foundSelectableTime) {
                System.out.println("❌ No selectable time found in any slot.");
            }

            // Click the booking button again
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Đặt hẹn')]")))
                    .click();

            // Wait and get the error message element
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(), 'Cảm ơn quý khách đã đặt hẹn tại')]")));

            // Assertion
            Assert.assertTrue(errorElement.isDisplayed(), "Error message not displayed.");

        } catch (Exception e) {
            logger.error("Booking test failed: {}", e.getMessage());
            Assert.fail("Booking test failed: " + e.getMessage());
        }
    }

}
