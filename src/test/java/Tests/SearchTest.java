package Tests;

import Base.BaseTest;
import Utils.LoginSetup;
import Utils.SearchUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
public class SearchTest extends BaseTest {

    @Test
    public void testSearch() {
        // Gọi hàm search từ SearchUtil
        try {
            SearchUtil.search(driver, "Sữa rửa mặt");
            System.out.println("Tìm kiếm thành công! Đã thực hiện tìm kiếm với từ khóa: Sữa rửa mặt.");
        } catch (Exception e) {
            Assert.fail("Tìm kiếm thất bại: " + e.getMessage());
        }
    }


}
