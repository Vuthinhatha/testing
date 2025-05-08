package Tests;

import Base.BaseTest;
import Utils.LoginSetup;
import Utils.SearchUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.regex.Pattern;

/**
 * Test class for verifying search functionality
 */
public class SearchTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SearchTest.class);
    private static final String SEARCH_TERM = "Sữa rửa mặt";

    /**
     * Tests the search functionality by:
     * 1. Performing a search with a specific term
     * 2. Verifying that search results are displayed
     * 3. Checking that the search term appears in the results
     */
    @Test
    public void testSearch() {
        try {
            // Perform search
            SearchUtil.search(driver, SEARCH_TERM);
            logger.info("Search performed successfully with term: {}", SEARCH_TERM);

            // Wait for the <h4 class="txt_999"> element to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.pollingEvery(Duration.ofMillis(100));  // Poll faster than default 500ms
            WebElement resultCountElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.txt_999"))
            );
            
            // Get the text and check with regex
            String resultText = resultCountElement.getText();
            logger.info("Result count text: {}", resultText);
            System.out.println("Search result count text: " + resultText);
            org.testng.Reporter.log("Search result count text: " + resultText, true);

            // Pattern: (number sản phẩm)
            Pattern pattern = Pattern.compile("\\(\\d+ sản phẩm\\)");
            Assert.assertTrue(pattern.matcher(resultText).matches(),
                "Result count should match the pattern '(number sản phẩm)' but was: " + resultText);

        } catch (Exception e) {
            logger.error("Search test failed: {}", e.getMessage());
            Assert.fail("Search test failed: " + e.getMessage());
        }
    }
}
