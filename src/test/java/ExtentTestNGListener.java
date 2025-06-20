import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ExtentTestNGListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // Generate a timestamp for unique report naming
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = "target/reports/ExtentReport_" + timeStamp + ".html";
        
        // Create directories if they don't exist
        File reportDir = new File("target/reports");
        File screenshotDir = new File("target/screenshots");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        // Initialize ExtentSparkReporter with unique report path
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setReportName("Login Test Report - " + timeStamp);
        spark.config().setDocumentTitle("TestNG Extent Report");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();
        extentTest.log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());
        try {
            // Retrieve WebDriver instance
            WebDriver driver = (WebDriver) result.getTestClass().getRealClass()
                    .getSuperclass().getDeclaredField("driver").get(result.getInstance());
            
            // Generate unique screenshot file name
            String fileName = "screenshot-" + UUID.randomUUID() + ".png";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("target/screenshots/" + fileName);
            
            // Copy screenshot to destination
            FileUtils.copyFile(screenshot, destination);
            
            // Attach screenshot to report with relative path
            extentTest.fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/" + fileName).build());
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            extentTest.log(Status.WARNING, "Failed to capture or attach screenshot: " + e.getMessage());
        } catch (Exception e) {
            extentTest.log(Status.WARNING, "Unexpected error during screenshot capture: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped: " + result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}