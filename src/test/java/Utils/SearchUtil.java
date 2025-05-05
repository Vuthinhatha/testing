package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchUtil {
    public static void search(WebDriver driver, String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Nhập từ khóa vào ô tìm kiếm
        try {
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search"))); // Thay bằng selector thực tế
            searchInput.clear();
            searchInput.sendKeys(keyword);
            System.out.println("Đã nhập từ khóa tìm kiếm: " + keyword);
        } catch (Exception e) {
            System.out.println("Không thể nhập từ khóa tìm kiếm: " + e.getMessage());
        }

        // Nhấp nút tìm kiếm
        try {
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[4]/div[2]/div[2]/form/div[2]/div/div[1]/div[2]/button/img"))); // Thay bằng selector thực tế
            searchButton.click();
            System.out.println("Đã nhấp nút tìm kiếm.");
        } catch (Exception e) {
            System.out.println("Không thể nhấp nút tìm kiếm: " + e.getMessage());
        }
    }
}
