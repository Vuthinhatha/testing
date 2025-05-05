import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class ThemSPvaoGio {
    public static FirefoxOptions options;
    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeTest
    public static void Setup() {
        options = new FirefoxOptions();
        options.addArguments("--disable-notifications"); // Chỉ giữ tham số cần thiết

        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver.exe");
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.get("https://hasaki.vn/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void teststeps() {
//        // Gọi login từ class LoginSetup
//        LoginSetup.login(driver, "0966265795", "Nhatha1112@"); // ⚡ Thay tài khoản thực
//
//        // ------ Thêm sản phẩm vào giỏ hàng ------
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
//
//        WebElement searchBox = driver.findElement(By.id("search"));
//        searchBox.sendKeys("sữa rửa mặt");
//        driver.findElement(By.xpath("//*[@id=\"search_mini_form\"]/div[2]/div/div[1]/div[2]/button/img")).click();
//
//        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//*[@id=\"6807733990533\"]/div[1]/strong")));
//        firstProduct.click();
//        // Cuộn đến phần tử sản phẩm
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].scrollIntoView(true);", firstProduct);
//
//        // Chờ phần tử có thể click và click vào sản phẩm
//        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
//
//        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div/div[7]/button[1]/div")));
//        addToCartButton.click();
//        try {
//            WebElement cartPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.cssSelector(".cart-popup")));
//            System.out.println("Thêm sản phẩm vào giỏ hàng thành công!");
//        } catch (Exception e) {
//            System.out.println("Không thấy popup giỏ hàng. Cần kiểm tra lại.");
//        }



        // Gọi login từ class LoginSetup
        LoginSetup.login(driver, "0966265795", "Nhatha1112@");

        // ------ Thêm sản phẩm vào giỏ hàng ------
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));

        WebElement searchBox = driver.findElement(By.id("search"));
        searchBox.sendKeys("sữa rửa mặt");
        driver.findElement(By.xpath("//*[@id=\"search_mini_form\"]/div[2]/div/div[1]/div[2]/button")).click();

        // Chờ danh sách sản phẩm xuất hiện
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'ProductGrid__grid')]")));

        // Tìm sản phẩm đầu tiên
        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'ProductGridItem__itemOuter')]//strong)[1]")));

        // Cuộn đến sản phẩm
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstProduct);

        // Click sản phẩm
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();

        // Thêm vào giỏ hàng
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div/div[7]/button[1]")));
        addToCartButton.click();

        // Kiểm tra popup giỏ hàng
        try {
            WebElement cartPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div[class*='cart-popup']")));
            System.out.println("Thêm sản phẩm vào giỏ hàng thành công!");
        } catch (Exception e) {
            System.out.println("Không thấy popup giỏ hàng: " + e.getMessage());
        }
    }
}
