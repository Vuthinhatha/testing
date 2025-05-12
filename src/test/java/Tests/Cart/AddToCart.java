package Tests.Cart;

import Base.BaseTest;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
    public void addToCart() {
        String searchString = "sữa rửa mặt";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            SearchUtil.search(driver, searchString);
            logger.info("Search performed successfully with term: {}", searchString);
            // Chờ danh sách sản phẩm xuất hiện
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//div[contains(@class, 'ProductGrid__grid')]")));
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
                    By.xpath("//div[contains(text(), 'Sản Phẩm đã được thêm vào giỏ hàng thành công')]")));

            // ✅ Assertion to verify
            Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed.");
            logger.info("Product added to cart successfully");

        } catch (Exception e) {
            logger.error("Add to cart failed: {}", e.getMessage());
            Assert.fail("Add to cart failed: " + e.getMessage());
        }

    }

}
