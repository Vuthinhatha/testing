package Tests;

import Base.BaseTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Random;

public class SignUp extends BaseTest {

    @Test
    public void openSignUpAndVerify() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Step 1: Create temp email
            String tempEmail = generateRandomEmail();
            String password = "12345";
            createAccount(tempEmail, password);
            String token = getToken(tempEmail, password);
            if (token == null) {
                throw new RuntimeException("Could not get token.");
            }

            // Step 2: Open the site and sign up
            WebElement signUpLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.popup-register")));
            signUpLink.click();

            // Step 3: Fill the email and trigger verification
            WebElement emailInput = driver.findElement(By.name("email"));
            emailInput.sendKeys(tempEmail);
            WebElement getCodeButton = driver.findElement(By.id("verifyUserName")); // Adjust selector if needed
            getCodeButton.click();

            // Step 4: Wait for email and extract code
            Thread.sleep(10000); // wait for email to arrive
            String messageId = waitForMessageId(token);
            String messageText = getMessageText(token, messageId);
            String code = extractCode(messageText);
            String passwordString = "flyingpig1234";
            String fullname = "Nguyen Van A";

            // Step 5: Fill verification code
            WebElement codeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".codeRegister")));
            codeInput.sendKeys(code);

            System.out.println("Filled verification code: " + code);

            // Step 6: Fill password
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(".reg-password")));
            passwordInput.sendKeys(passwordString);
            System.out.println("Filled password: " + passwordString);

            // Step 7: Fill fullname
            WebElement fullnameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(".reg-password")));
            fullnameInput.sendKeys(fullname);
            System.out.println("Filled fullname: " + fullname);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateRandomEmail() {
        Random random = new Random();
        StringBuilder email = new StringBuilder();
        for (int i = 0; i < 3; i++) email.append(random.nextInt(10));
        for (int i = 0; i < 5; i++) email.append((char) ('a' + random.nextInt(26)));
        return email.append("@chefalicious.com").toString();
    }

    private void createAccount(String email, String password) throws IOException {
        String jsonInput = String.format("{\"address\":\"%s\",\"password\":\"%s\"}", email, password);
        HttpURLConnection con = (HttpURLConnection) new URL("https://api.mail.tm/accounts").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("Create Account - Response Code: " + con.getResponseCode());
    }

    private String getToken(String email, String password) throws IOException {
        String jsonInput = String.format("{\"address\":\"%s\",\"password\":\"%s\"}", email, password);
        HttpURLConnection con = (HttpURLConnection) new URL("https://api.mail.tm/token").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String response = readResponse(con);
            return new JSONObject(response).getString("token");
        }
        return null;
    }

    private String waitForMessageId(String token) throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            URL url = new URL("https://api.mail.tm/messages");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + token);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String response = readResponse(con);
                JSONArray messages = new JSONObject(response).getJSONArray("hydra:member");
                if (messages.length() > 0) {
                    return messages.getJSONObject(0).getString("id");
                }
            }
            Thread.sleep(2000); // Wait 2 seconds before retrying
        }
        throw new RuntimeException("No email message received.");
    }

    private String getMessageText(String token, String messageId) throws IOException {
        URL url = new URL("https://api.mail.tm/messages/" + messageId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String response = readResponse(con);
            return new JSONObject(response).optString("text", "");
        }
        return "";
    }

    private String extractCode(String text) {
        return text.replaceAll("(?s).*?(\\d{6}).*", "$1");
    }

    private String readResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return response.toString();
    }
}
