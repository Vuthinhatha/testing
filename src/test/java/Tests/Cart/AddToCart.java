package Tests.Cart;

import Base.BaseTest;

import java.time.Duration;

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

public class AddToCart extends BaseTest {
        private static final Logger logger = LogManager.getLogger(AddToCart.class);

        @Test
        public void addToCartValid() {
                String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
                        SearchUtil.search(driver, searchString);
                        logger.info("Search performed successfully with term: {}", searchString);
                        // Chờ danh sách sản phẩm xuất hiện
                        wait.until(ExpectedConditions
                                        .presenceOfElementLocated(
                                                        By.xpath("//div[contains(@class, 'ProductGrid__grid')]")));
                        logger.info("Products list displayed successfully");
                        System.out.println("Products list displayed successfully");

                        // Tìm sản phẩm đầu tiên
                        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath(
                                                        "(//div[contains(@class, 'ProductGridItem__itemOuter')]//a[contains(@class, 'block_info_item_sp')])[1]")));

                        logger.info("First product found successfully");
                        System.out.println("First product found successfully");

                        // Cuộn đến sản phẩm
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProduct);
                        logger.info("First product scrolled into view successfully");
                        System.out.println("First product scrolled into view successfully");

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        // wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();

                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        // Lấy title sản phẩm
                        WebElement title = driver
                                        .findElement(By.xpath("//h1[@class='text-[19px] font-medium leading-[22px]']"));
                        String titleText = title.getText();

                        // Thêm vào giỏ hàng
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[contains(., 'Giỏ hàng')]"))).click();
                        // // ✅ Wait for success message
                        // WebElement successMessage =
                        // wait.until(ExpectedConditions.visibilityOfElementLocated(
                        // By.xpath("//div[contains(text(), 'Sản Phẩm đã được thêm vào giỏ hàng thành
                        // công')]")));

                        // // ✅ Assertion to verify
                        // Assert.assertTrue(successMessage.isDisplayed(), "Success message not
                        // displayed.");
                        // logger.info("Product added to cart successfully");
                        // System.out.println("Product added to cart successfully");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[@aria-label='Cart Nav']"))).click();

                        // Locate the input with the expected value
                        // Locate the correct <tr> by matching the product title inside <a>
                        WebElement cartRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                                        By.xpath("//tr[.//a[contains(text(), \"" + titleText + "\")]]")));

                        // Now locate the input within this specific <tr>
                        WebElement quantityInput = cartRow.findElement(By.xpath(".//input[@type='text']"));

                        // Get and assert the value
                        String inputValue = quantityInput.getAttribute("value");
                        Assert.assertEquals(inputValue, "1", "Expected quantity to be 1 for product: " + titleText);

                        logger.info("Verified that the quantity input for '{}' has value 1.", titleText);
                        System.out.println("Verified that the quantity input for '" + titleText + "' has value 1.");

                } catch (Exception e) {
                        logger.error("Add to cart failed: {}", e.getMessage());
                        Assert.fail("Add to cart failed: " + e.getMessage());
                }

        }

        @Test
        public void addFromHomepage() {
                // String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
                        // SearchUtil.search(driver, searchString);
                        // logger.info("Search performed successfully with term: {}", searchString);
                        // Chờ danh sách sản phẩm xuất hiện
                        // wait.until(ExpectedConditions
                        // .presenceOfElementLocated(By.xpath("//div[contains(@class,
                        // 'ProductGrid__grid')]")));
                        // logger.info("Products list displayed successfully");
                        // System.out.println("Products list displayed successfully");

                        // Tìm sản phẩm đầu tiên
                        // WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                        // By.xpath(
                        // "(//div[contains(@class, 'ProductGridItem__itemOuter')]//a[contains(@class,
                        // 'block_info_item_sp')])[1]")));

                        // logger.info("First product found successfully");
                        // System.out.println("First product found successfully");

                        // Cuộn đến sản phẩm
                        // JavascriptExecutor js = (JavascriptExecutor) driver;
                        // js.executeScript("arguments[0].scrollIntoView({block: 'center'});",
                        // firstProduct);
                        // logger.info("First product scrolled into view successfully");
                        // System.out.println("First product scrolled into view successfully");

                        JavascriptExecutor js = (JavascriptExecutor) driver;

                        // Scroll down by 500 pixels vertically
                        js.executeScript("window.scrollBy(0, 500);");

                        WebElement firstItem = driver
                                        .findElement(By.xpath("(//div[contains(@class, 'item_goiy')])[1]"));

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstItem)).click();
                        // wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();

                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        // Lấy title sản phẩm
                        WebElement title = driver
                                        .findElement(By.xpath("//h1[@class='text-[19px] font-medium leading-[22px]']"));
                        String titleText = title.getText();

                        // Thêm vào giỏ hàng
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[contains(., 'Giỏ hàng')]"))).click();
                        // // ✅ Wait for success message
                        // WebElement successMessage =
                        // wait.until(ExpectedConditions.visibilityOfElementLocated(
                        // By.xpath("//div[contains(text(), 'Sản Phẩm đã được thêm vào giỏ hàng thành
                        // công')]")));

                        // // ✅ Assertion to verify
                        // Assert.assertTrue(successMessage.isDisplayed(), "Success message not
                        // displayed.");
                        // logger.info("Product added to cart successfully");
                        // System.out.println("Product added to cart successfully");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[@aria-label='Cart Nav']"))).click();

                        // Locate the input with the expected value
                        // Locate the correct <tr> by matching the product title inside <a>
                        WebElement cartRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                                        By.xpath("//tr[.//a[contains(text(), \"" + titleText + "\")]]")));

                        // Now locate the input within this specific <tr>
                        WebElement quantityInput = cartRow.findElement(By.xpath(".//input[@type='text']"));

                        // Get and assert the value
                        String inputValue = quantityInput.getAttribute("value");
                        Assert.assertEquals(inputValue, "1", "Expected quantity to be 1 for product: " + titleText);

                        logger.info("Verified that the quantity input for '{}' has value 1.", titleText);
                        System.out.println("Verified that the quantity input for '" + titleText + "' has value 1.");

                } catch (Exception e) {
                        logger.error("Add to cart failed: {}", e.getMessage());
                        Assert.fail("Add to cart failed: " + e.getMessage());
                }

        }

        @Test
        public void addMoreSameProduct() {
                String searchString = "Dầu gội khô";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
                        SearchUtil.search(driver, searchString);
                        logger.info("Search performed successfully with term: {}", searchString);
                        // Chờ danh sách sản phẩm xuất hiện
                        wait.until(ExpectedConditions
                                        .presenceOfElementLocated(
                                                        By.xpath("//div[contains(@class, 'ProductGrid__grid')]")));
                        logger.info("Products list displayed successfully");
                        System.out.println("Products list displayed successfully");

                        // Tìm sản phẩm đầu tiên
                        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath(
                                                        "(//div[contains(@class, 'ProductGridItem__itemOuter')]//a[contains(@class, 'block_info_item_sp')])[1]")));

                        logger.info("First product found successfully");
                        System.out.println("First product found successfully");

                        // Cuộn đến sản phẩm
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProduct);
                        logger.info("First product scrolled into view successfully");
                        System.out.println("First product scrolled into view successfully");

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        // wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();

                        // Lấy title sản phẩm
                        WebElement title = driver
                                        .findElement(By.xpath("//h1[@class='text-[19px] font-medium leading-[22px]']"));
                        String titleText = title.getText();

                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        // Thêm vào giỏ hàng
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[contains(., 'Giỏ hàng')]"))).click();
                        // // ✅ Wait for success message
                        // WebElement successMessage =
                        // wait.until(ExpectedConditions.visibilityOfElementLocated(
                        // By.xpath("//div[contains(text(), 'Sản Phẩm đã được thêm vào giỏ hàng thành
                        // công')]")));

                        // // ✅ Assertion to verify
                        // Assert.assertTrue(successMessage.isDisplayed(), "Success message not
                        // displayed.");
                        // logger.info("Product added to cart successfully");
                        // System.out.println("Product added to cart successfully");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[@aria-label='Cart Nav']"))).click();

                        // Locate the input with the expected value
                        // Locate the correct <tr> by matching the product title inside <a>
                        WebElement cartRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                                        By.xpath("//tr[.//a[contains(text(), \"" + titleText + "\")]]")));

                        // Now locate the input within this specific <tr>
                        WebElement quantityInput = cartRow.findElement(By.xpath(".//input[@type='text']"));

                        // Get and assert the value
                        String inputValue = quantityInput.getAttribute("value");
                        Assert.assertEquals(inputValue, "2", "Expected quantity to be 2 for product: " + titleText);

                        logger.info("Verified that the quantity input for '{}' has value 2.", titleText);
                        System.out.println("Verified that the quantity input for '" + titleText + "' has value 2.");

                } catch (Exception e) {
                        logger.error("Add to cart failed: {}", e.getMessage());
                        Assert.fail("Add to cart failed: " + e.getMessage());
                }

        }

        @Test
        public void addMoreUniqueToCart() {
                String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
                        SearchUtil.search(driver, searchString);
                        logger.info("Search performed successfully with term: {}", searchString);
                        // Chờ danh sách sản phẩm xuất hiện
                        wait.until(ExpectedConditions
                                        .presenceOfElementLocated(
                                                        By.xpath("//div[contains(@class, 'ProductGrid__grid')]")));
                        logger.info("Products list displayed successfully");
                        System.out.println("Products list displayed successfully");

                        // Tìm sản phẩm đầu tiên
                        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath(
                                                        "(//div[contains(@class, 'ProductGridItem__itemOuter')]//a[contains(@class, 'block_info_item_sp')])[1]")));

                        logger.info("First product found successfully");
                        System.out.println("First product found successfully");

                        // Cuộn đến sản phẩm
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProduct);
                        logger.info("First product scrolled into view successfully");
                        System.out.println("First product scrolled into view successfully");

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        // Thêm vào giỏ hàng
                        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[.//div[contains(text(), 'Giỏ hàng')]]")));
                        addToCartButton.click();
                        // ✅ Wait for success message
                        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//div[contains(text(), 'Sản phẩm chỉ được mua tối đa 1')]")));

                        // ✅ Assertion to verify
                        Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed.");
                        logger.info("Message displayed successfully");
                        System.out.println("Message displayed successfully");

                } catch (Exception e) {
                        logger.error("Add more unique to cart failed: {}", e.getMessage());
                        Assert.fail("Add more unique to cart failed: " + e.getMessage());
                }

        }

        @Test
        public void addToCartWithVariant() {
                String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
                        SearchUtil.search(driver, searchString);
                        logger.info("Search performed successfully with term: {}", searchString);
                        // Chờ danh sách sản phẩm xuất hiện
                        wait.until(ExpectedConditions
                                        .presenceOfElementLocated(
                                                        By.xpath("//div[contains(@class, 'ProductGrid__grid')]")));
                        logger.info("Products list displayed successfully");
                        System.out.println("Products list displayed successfully");

                        // Tìm sản phẩm đầu tiên
                        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath(
                                                        "(//div[contains(@class, 'ProductGridItem__itemOuter')]//a[contains(@class, 'block_info_item_sp')])[1]")));

                        logger.info("First product found successfully");
                        System.out.println("First product found successfully");

                        // Cuộn đến sản phẩm
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProduct);
                        logger.info("First product scrolled into view successfully");
                        System.out.println("First product scrolled into view successfully");

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        // wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();

                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[text()='236ml']"))).click();

                        // Thêm vào giỏ hàng
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[contains(., 'Giỏ hàng')]"))).click();
                        // // ✅ Wait for success message
                        // WebElement successMessage =
                        // wait.until(ExpectedConditions.visibilityOfElementLocated(
                        // By.xpath("//div[contains(text(), 'Sản Phẩm đã được thêm vào giỏ hàng thành
                        // công')]")));

                        // // ✅ Assertion to verify
                        // Assert.assertTrue(successMessage.isDisplayed(), "Success message not
                        // displayed.");
                        // logger.info("Product added to cart successfully");
                        // System.out.println("Product added to cart successfully");

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[@aria-label='Cart Nav']"))).click();

                        // Locate the input with the expected value
                        WebElement quantityInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                                        By.xpath("//input[@type='text' and @value='1']")));

                        // Or if you can't use the value attribute in XPath (e.g., it might change),
                        // locate and then check value via getAttribute
                        String inputValue = quantityInput.getAttribute("value");
                        Assert.assertEquals(inputValue, "1", "Expected quantity to be 1");
                        logger.info("Verified that the quantity input has value 1.");
                        System.out.println("Verified that the quantity input has value 1.");

                } catch (Exception e) {
                        logger.error("Add to cart failed: {}", e.getMessage());
                        Assert.fail("Add to cart failed: " + e.getMessage());
                }

        }

        @Test
        public void deleteFromCart() {
                String titleText = "";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
                        // Chờ danh sách sản phẩm xuất hiện
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[@aria-label='Cart Nav']"))).click();

                        // XPath to find the <tr> containing the product title text inside <a> or inside
                        // .block_info_item_sp a
                        String xpath = "//tr[.//a[contains(text(),'" + titleText + "')]]//button[@aria-label='Xóa']";

                        // Find delete button for that product row
                        WebElement deleteBtn = driver.findElement(By.xpath(xpath));

                        // Click the delete button
                        deleteBtn.click();

                        // Optional short wait if needed for UI update
                        Thread.sleep(500); // or better use explicit wait for some condition

                        // Verify the row is removed
                        Assert.assertFalse(deleteBtn.isDisplayed(), "Cart row should be removed");

                        logger.info("Product deleted from cart successfully");
                        System.out.println("Product deleted from cart successfully");

                } catch (Exception e) {
                        logger.error("Delete from cart failed: {}", e.getMessage());
                        Assert.fail("Delete from cart failed: " + e.getMessage());
                }

        }

        @Test
        public void addToCartNoLogin() {
                String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        SearchUtil.search(driver, searchString);
                        logger.info("Search performed successfully with term: {}", searchString);
                        // Chờ danh sách sản phẩm xuất hiện
                        wait.until(ExpectedConditions
                                        .presenceOfElementLocated(
                                                        By.xpath("//div[contains(@class, 'ProductGrid__grid')]")));
                        logger.info("Products list displayed successfully");
                        System.out.println("Products list displayed successfully");

                        // Tìm sản phẩm đầu tiên
                        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath(
                                                        "(//div[contains(@class, 'ProductGridItem__itemOuter')]//a[contains(@class, 'block_info_item_sp')])[1]")));

                        logger.info("First product found successfully");
                        System.out.println("First product found successfully");

                        // Cuộn đến sản phẩm
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProduct);
                        logger.info("First product scrolled into view successfully");
                        System.out.println("First product scrolled into view successfully");

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        // Thêm vào giỏ hàng
                        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[.//div[contains(text(), 'Giỏ hàng')]]")));
                        addToCartButton.click();
                        // ✅ Wait for success message
                        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//p[contains(text(), 'Đăng nhập với')]")));

                        // ✅ Assertion to verify
                        Assert.assertTrue(successMessage.isDisplayed(), "Login message not displayed.");
                        logger.info("Login message displayed successfully");
                        System.out.println("Login message displayed successfully");

                } catch (Exception e) {
                        logger.error("Add to cart failed: {}", e.getMessage());
                        Assert.fail("Add to cart failed: " + e.getMessage());
                }

        }

        @Test
        public void clickToCartNoLogin() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']")))
                                        .click();

                        // ✅ Wait for success message
                        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//div[contains(text(), 'Đăng nhập với')]")));

                        // ✅ Assertion to verify
                        Assert.assertTrue(successMessage.isDisplayed(), "Login message not displayed.");
                        logger.info("Login message displayed successfully");
                        System.out.println("Login message displayed successfully");

                } catch (Exception e) {
                        logger.error("Add to cart failed: {}", e.getMessage());
                        Assert.fail("Add to cart failed: " + e.getMessage());
                }

        }

        @Test
        public void reduceQuantity() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
                        // Chờ danh sách sản phẩm xuất hiện
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[@aria-label='Cart Nav']"))).click();

                        // Locate the input
                        WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//input[@type='text' and contains(@class,'outline-none')]")));

                        // Clear and enter new value
                        quantityInput.clear();
                        quantityInput.sendKeys("1");

                        // Press TAB to trigger form confirm or blur event
                        quantityInput.sendKeys(Keys.TAB);

                        // Optional short wait if needed for UI update
                        Thread.sleep(500); // or better use explicit wait for some condition

                        // Re-select input to verify updated value
                        quantityInput = driver.findElement(
                                        By.xpath("//input[@type='text' and contains(@class,'outline-none')]"));
                        String updatedValue = quantityInput.getAttribute("value");
                        Assert.assertEquals(updatedValue, "1", "Quantity updated to 1 successfully");

                        logger.info("Quantity updated to 1 successfully");
                        System.out.println("Quantity updated to 1 successfully");

                } catch (Exception e) {
                        logger.error("Reduce quantity failed: {}", e.getMessage());
                        Assert.fail("Reduce quantity failed: " + e.getMessage());
                }

        }

        @Test
        public void updateQuantity() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                try {
                        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
                        // Chờ danh sách sản phẩm xuất hiện
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[@aria-label='Cart Nav']"))).click();

                        // Locate the input
                        WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//input[@type='text' and contains(@class,'outline-none')]")));

                        // Clear and enter new value
                        quantityInput.clear();
                        quantityInput.sendKeys("3");

                        // Press TAB to trigger form confirm or blur event
                        quantityInput.sendKeys(Keys.TAB);

                        // Optional short wait if needed for UI update
                        Thread.sleep(500); // or better use explicit wait for some condition

                        // Re-select input to verify updated value
                        quantityInput = driver.findElement(
                                        By.xpath("//input[@type='text' and contains(@class,'outline-none')]"));
                        String updatedValue = quantityInput.getAttribute("value");
                        Assert.assertEquals(updatedValue, "3", "Quantity updated to 3 successfully");

                        logger.info("Quantity updated to 3 successfully");
                        System.out.println("Quantity updated to 3 successfully");

                } catch (Exception e) {
                        logger.error("Update quantity failed: {}", e.getMessage());
                        Assert.fail("Update quantity failed: " + e.getMessage());
                }

        }

}
