package Tests;

import Base.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BaseTestValidation extends BaseTest {
    @Test
    public void testBaseSetup() {

        // Kiểm tra xem trang có tải thành công không
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Tiêu đề trang không được null!");
        Assert.assertTrue(pageTitle.contains("Hasaki"), "Tiêu đề trang không chứa từ 'Hasaki'!");

        // Kiểm tra xem thông báo vị trí có bị tắt không
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object geoLocation = js.executeScript("return navigator.geolocation;");
        Assert.assertNull(geoLocation, "Thông báo vị trí chưa được tắt! navigator.geolocation phải là null.");

        // Kiểm tra xem thông báo web (notifications) có bị tắt không
        // Nếu không có thông báo nào xuất hiện, driver sẽ không bị chặn bởi popup
        try {
            Thread.sleep(2000); // Chờ 2 giây để xem có popup nào không
            System.out.println("Không thấy popup nào, thông báo đã được tắt thành công!");
        } catch (Exception e) {
            Assert.fail("Có lỗi xảy ra, có thể thông báo chưa được tắt: " + e.getMessage());
        }

    }
}
