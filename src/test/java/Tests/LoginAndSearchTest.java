package Tests;


import Base.BaseTest;
import Utils.LoginSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginAndSearchTest extends BaseTest {
    @Test
    public void testLoginAndSearch() {
        // Khởi tạo WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Bước 1: Đăng nhập
        try {
            LoginSetup.login(driver, "0966265795", "Nhatha1112@");
            System.out.println("Đăng nhập thành công!");
        } catch (Exception e) {
            Assert.fail("Đăng nhập thất bại: " + e.getMessage());
        }

        // Bước 2: Tìm kiếm
        try {
            // Chờ ô tìm kiếm hiển thị và có thể nhập
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("search")));

            // Focus vào ô tìm kiếm
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].focus();", searchBox);

            // Xóa ô tìm kiếm (nếu có dữ liệu cũ)
            searchBox.clear();

            // Nhập từ khóa tìm kiếm
            searchBox.sendKeys("Sữa rửa mặt");
            System.out.println("Đã nhập từ khóa: " + searchBox.getAttribute("value"));

            // Thêm độ trễ 2 giây sau khi nhập để bạn có thời gian nhìn rõ
            try {
                Thread.sleep(2000); // 2000 milliseconds = 2 giây
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Nhấp nút tìm kiếm
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'btn_submit_search')]")));
            searchButton.click();

            // Chờ danh sách sản phẩm xuất hiện
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class, 'item')]")));

            System.out.println("Tìm kiếm thành công! Đã thực hiện tìm kiếm với từ khóa: Sữa rửa mặt.");
        } catch (Exception e) {
            Assert.fail("Tìm kiếm thất bại: " + e.getMessage());
        }

        // Bước 3: Thêm sản phẩm vào giỏ hàng
        try {
//            // Chờ danh sách sản phẩm xuất hiện
//            wait.until(ExpectedConditions.presenceOfElementLocated(
//                    By.xpath("//div[contains(@class, 'item')]")));
//
//            // Tìm sản phẩm đầu tiên (dùng thẻ <a> chứa href dẫn đến sản phẩm)
//            WebElement firstProductLink = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("(//div[contains(@class, 'item')]//a[contains(@href, '/san-pham/')])[1]")));
//
//            // Cuộn xuống một chút để thấy sản phẩm
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProductLink);
//
//            // Nhấp vào sản phẩm
//            firstProductLink.click();
//            System.out.println("Đã nhấp vào sản phẩm đầu tiên.");
//
//            wait.until(ExpectedConditions.presenceOfElementLocated(
//                    By.xpath("//h1[contains(@class, 'font-medium')]")));
//
//// Chờ nút "Thêm vào giỏ hàng"
//            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
//                    By.cssSelector("button.h-\\[43px\\]:nth-child(2)")));
//
//// Kiểm tra trạng thái disabled
//            wait.until(ExpectedConditions.not(
//                    ExpectedConditions.attributeContains(addToCartButton, "disabled", "true")));
//
//// Nhấp nút
//            addToCartButton.click();
//            System.out.println("Đã nhấp nút Thêm vào giỏ.");


            // Chờ danh sách sản phẩm xuất hiện
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class, 'item')]")));

            // Tìm sản phẩm đầu tiên (dùng thẻ <a> chứa href dẫn đến sản phẩm)
            WebElement firstProductLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[contains(@class, 'item')]//a[contains(@href, '/san-pham/')])[1]")));

            // Cuộn xuống một chút để thấy sản phẩm
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProductLink);

            // Nhấp vào sản phẩm
            js.executeScript("arguments[0].click();", firstProductLink);
            System.out.println("Đã nhấp vào sản phẩm đầu tiên.");

            // Chờ trang chi tiết sản phẩm tải xong
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div/h1")));

            // Chờ nút "Thêm vào giỏ hàng"
            WebElement addToCartButton = driver.findElement(
                    By.xpath("//button[contains(@class, 'bg-primary')]"));

            // Cuộn đến nút
            // Cuộn đến nút
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartButton);

// Nhấp nút bằng JavaScript để đảm bảo kích hoạt
            js.executeScript("arguments[0].click();", addToCartButton);
            System.out.println("Đã nhấp nút Thêm vào giỏ.");


            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(), 'Đã thêm vào giỏ hàng')] | //span[contains(@class, 'cart-count')] | //div[contains(@class, 'toast')]")));
                System.out.println("Sản phẩm đã được thêm vào giỏ hàng!");
            } catch (Exception e) {
                System.out.println("Không thấy xác nhận thêm vào giỏ: " + e.getMessage());
                // In thêm thông tin debug
                System.out.println("URL hiện tại: " + driver.getCurrentUrl());
                System.out.println("Nút hiển thị: " + addToCartButton.isDisplayed());
                System.out.println("Nút khả dụng: " + addToCartButton.isEnabled());
            }
        } catch (Exception e) {
            Assert.fail("Thêm sản phẩm vào giỏ thất bại: " + e.getMessage());
        }
    }
}
