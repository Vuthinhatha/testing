import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
public class ThemGio extends BaseSetup {
    public void themSanPhamVaoGio(String tenSanPham) {
        // Sửa lỗi cú pháp: thêm dấu ngoặc ()
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
        WebElement searchBox = driver.findElement(By.id("search"));
        searchBox.sendKeys(tenSanPham);
        driver.findElement(By.xpath("//*[@id=\"search_mini_form\"]//button")).click();

        // Sửa lỗi cú pháp: thêm dấu ngoặc ()
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'product-item')]")));
        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'product-item')]//strong)[1]")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProduct);
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();

        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'add-to-cart')]")));
        addToCartButton.click();

        try {
            WebElement cartPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div[class*='cart-popup']")));
            System.out.println("Thêm sản phẩm vào giỏ hàng thành công!");
        } catch (Exception e) {
            System.out.println("Không thấy popup giỏ hàng: " + e.getMessage());
        }
    }

  //  @Test
    public void teststeps() {
        LoginSetup.login(driver, "0966265795", "Nhatha1112@");
        themSanPhamVaoGio("sữa rửa mặt");
    }
}
