package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PayFormFill {

    // Main method with all optional flags
    public static void fillForm(WebDriver driver, String phone, String name, String province, String district,
            String address, boolean skipProvince, boolean skipDistrict, boolean skipAddress, boolean skipContinue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement phoneInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Số điện thoại']")));
        phoneInput.sendKeys(phone);

        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Họ và tên']")));
        nameInput.sendKeys(name);

        // Province
        if (!skipProvince) {
            WebElement provinceDropdownButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(., 'Chọn Tỉnh/ TP, Quận/ Huyện')]")));
            provinceDropdownButton.click();

            WebElement provinceOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@role, 'option') and contains(text(), '" + province + "')]")));
            provinceOption.click();
        }

        // District
        if (!skipDistrict) {
            WebElement districtDropdownButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(., 'Chọn Phường/ Xã')]")));
            districtDropdownButton.click();

            WebElement districtOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@role, 'option') and contains(text(), '" + district + "')]")));
            districtOption.click();
        }

        // Address
        if (!skipAddress) {
            WebElement addressInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(., 'Số nhà + Tên đường')]")));
            addressInput.click();

            WebElement addressInputField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Nhập vị trí của bạn']")));
            addressInputField.sendKeys(address);
        }

        // Continue
        if (!skipContinue) {
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='button' and contains(text(), 'Tiếp tục')]"))).click();
        }
    }

    // Overloaded method: All skips default to false
    public static void fillForm(WebDriver driver, String phone, String name, String province, String district,
            String address) {
        fillForm(driver, phone, name, province, district, address, false, false, false, false);
    }
}
