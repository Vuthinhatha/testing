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

        @Test(priority = 0)
        public void clickToCartNoLogin() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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

        @Test(priority = 1)
        public void addToCartValid() {
                String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "0833204787", "123456");
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

                        Thread.sleep(2000);

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        // wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();

                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        Thread.sleep(2000);

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

        @Test(priority = 2)
        public void addMoreSameProduct() {
                String searchString = "Dầu gội khô";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "0833204787", "123456");
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

                        Thread.sleep(2000);

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        // wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();

                        Thread.sleep(2000);

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

        @Test(priority = 3)
        public void addMoreUniqueToCart() {
                String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "0833204787", "123456");
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

                        Thread.sleep(2000);

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        Thread.sleep(2000);

                        // Thêm vào giỏ hàng
                        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[.//div[contains(text(), 'Giỏ hàng')]]")));
                        addToCartButton.click();
                        // ✅ Wait for success message
                        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//div[contains(text(), 'Sản phẩm chỉ được mua tối đa là 1')]")));

                        // ✅ Assertion to verify
                        Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed.");
                        logger.info("Message displayed successfully");
                        System.out.println("Message displayed successfully");

                } catch (Exception e) {
                        logger.error("Add more unique to cart failed: {}", e.getMessage());
                        Assert.fail("Add more unique to cart failed: " + e.getMessage());
                }

        }

        @Test(priority = 4)
        public void reduceQuantity() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
            try {
                LoginSetup.login(driver, "0833204787", "123456");
        
                // Open cart
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']"))).click();

                Thread.sleep(2000);
        
                // Wait for quantity input field and read current value
                WebElement quantityInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@type='text' and contains(@class,'outline-none')]")));
        
                String beforeValue = quantityInput.getAttribute("value");
                System.out.println("🔢 Quantity before click: " + beforeValue);
        
                // Click decrease button
                WebElement decreaseBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@aria-label='Descrease btn']")));
                decreaseBtn.click();
        
                // Wait a moment for UI to update or use explicit wait if there's a spinner
                Thread.sleep(1000); // optionally replace with wait for input value change
        
                // Get updated quantity
                quantityInput = driver.findElement(
                        By.xpath("//input[@type='text' and contains(@class,'outline-none')]"));
                String afterValue = quantityInput.getAttribute("value");
                System.out.println("🔢 Quantity after click: " + afterValue);
        
                // Check expected value change
                int before = Integer.parseInt(beforeValue);
                int after = Integer.parseInt(afterValue);
                Assert.assertEquals(after, before - 1, "Quantity should decrease by 1");
        
                logger.info("✅ Quantity successfully reduced from {} to {}", before, after);
        
            } catch (Exception e) {
                logger.error("❌ Reduce quantity failed: {}", e.getMessage());
                Assert.fail("Reduce quantity failed: " + e.getMessage());
            }
        }
        
        @Test(priority = 5)
        public void increaseQuantity() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
            try {
                LoginSetup.login(driver, "0833204787", "123456");
        
                // Open cart
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']"))).click();
                
                Thread.sleep(2000);
        
                // Wait for quantity input field and read current value
                WebElement quantityInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@type='text' and contains(@class,'outline-none')]")));
        
                String beforeValue = quantityInput.getAttribute("value");
                System.out.println("🔢 Quantity before increase: " + beforeValue);
        
                // Click increase button
                WebElement increaseBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@aria-label='Increase btn']")));
                increaseBtn.click();
        
                // Wait briefly for UI update
                Thread.sleep(1000); // Or better: wait for input value to change
        
                // Get updated quantity
                quantityInput = driver.findElement(
                        By.xpath("//input[@type='text' and contains(@class,'outline-none')]"));
                String afterValue = quantityInput.getAttribute("value");
                System.out.println("🔢 Quantity after increase: " + afterValue);
        
                // Assert that it increased by 1
                int before = Integer.parseInt(beforeValue);
                int after = Integer.parseInt(afterValue);
                Assert.assertEquals(after, before + 1, "Quantity should increase by 1");
        
                logger.info("✅ Quantity successfully increased from {} to {}", before, after);
        
            } catch (Exception e) {
                logger.error("❌ Increase quantity failed: {}", e.getMessage());
                Assert.fail("Increase quantity failed: " + e.getMessage());
            }
        }
          
        @Test(priority = 6)
        public void deleteFromCart() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
            try {
                LoginSetup.login(driver, "0833204787", "123456");
        
                // Open the cart
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//img[@src='https://media.hcdn.vn/hsk/icon/icon_header__cart.png']"))).click();
        
                Thread.sleep(2000); // Slight wait for cart to load
        
                // Locate the row with the specific product title
                WebElement productRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//tr[.//a[contains(text(), 'Sữa Rửa Mặt CeraVe Sạch Sâu Cho Da Thường Đến Da Dầu 473ml')] ]")));
        
                // Click the corresponding "Xóa" button in that row
                WebElement deleteBtn = productRow.findElement(
                        By.xpath(".//button[contains(text(), 'Xóa')]"));
                deleteBtn.click();
        
                // Wait until the row is no longer present
                boolean rowGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//tr[.//a[contains(text(), 'Sữa Rửa Mặt CeraVe Sạch Sâu Cho Da Thường Đến Da Dầu 473ml')] ]")));
        
                Assert.assertTrue(rowGone, "Product row should be removed from the cart");
        
                logger.info("✅ Product deleted from cart successfully");
                System.out.println("✅ Product deleted from cart successfully");
        
            } catch (Exception e) {
                logger.error("❌ Delete from cart failed: {}", e.getMessage());
                Assert.fail("Delete from cart failed: " + e.getMessage());
            }
        }
        
        @Test(priority = 7)
        public void addToCartNoLogin() {
                String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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

                        Thread.sleep(2000);

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

        @Test(priority = 8)
        public void addToCartWithVariant() {
                String searchString = "nước tẩy trang";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        LoginSetup.login(driver, "0833204787", "123456");
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

                        Thread.sleep(2000);

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();
                        // wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();

                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        Thread.sleep(3000);

                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//a[text()='140ml']"))).click();
                        
                        Thread.sleep(2000);

                        // Thêm vào giỏ hàng
                        wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//button[contains(., 'Giỏ hàng')]"))).click();

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

        @Test(priority = 9)
        public void addFromHomepage() {
                // String searchString = "sữa rửa mặt";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                        js.executeScript("window.scrollBy(0, 1500);");

                        Thread.sleep(2000);

                        WebElement firstItem = driver
                                        .findElement(By.xpath("(//div[contains(@class, 'item_goiy')])[1]"));

                        // Click sản phẩm
                        wait.until(ExpectedConditions.visibilityOf(firstItem)).click();
                        // wait.until(ExpectedConditions.visibilityOf(firstProduct)).click();

                        logger.info("First product clicked successfully");
                        System.out.println("First product clicked successfully");

                        Thread.sleep(2000);

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

}
