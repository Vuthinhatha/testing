package TempMailAPI;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetMailMessages {

    public static void main(String[] args) {
        try {
            String email = generateRandomEmail();
            String password = "12345";

            // Step 1: Create account
            createAccount(email, password);
            Thread.sleep(1000); // Give some time for the account to register

            // Step 2: Get token
            String token = getToken(email, password);
            if (token == null) {
                System.out.println("Failed to retrieve token.");
                return;
            }
            System.out.println("Access Token: " + token);

            // Step 3: Get messages
            String messageId = getFirstMessageId(token);
            if (messageId == null) {
                System.out.println("empty");
                return;
            }

            // Step 4: Get message content
            String messageText = getMessageText(token, messageId);
            System.out.println("Message Text: " + messageText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAccount(String email, String password) throws IOException {
        String jsonInput = String.format("{\"address\":\"%s\",\"password\":\"%s\"}", email, password);

        URL url = new URL("https://api.mail.tm/accounts");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = con.getResponseCode();
        System.out.println("Create Account - Response Code: " + responseCode);
    }

    private static String getToken(String email, String password) throws IOException {
        String jsonInput = String.format("{\"address\":\"%s\",\"password\":\"%s\"}", email, password);

        URL url = new URL("https://api.mail.tm/token");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String response = readResponse(con);
            JSONObject json = new JSONObject(response);
            return json.getString("token");
        }
        return null;
    }

    private static String getFirstMessageId(String token) throws IOException {
        URL url = new URL("https://api.mail.tm/messages");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String response = readResponse(con);
            JSONObject json = new JSONObject(response);
            JSONArray messages = json.getJSONArray("hydra:member");

            if (messages.length() > 0) {
                JSONObject firstMessage = messages.getJSONObject(0);
                return firstMessage.getString("id");
            }
        }
        return null;
    }

    private static String getMessageText(String token, String messageId) throws IOException {
        URL url = new URL("https://api.mail.tm/messages/" + messageId);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            String response = readResponse(con);
            JSONObject json = new JSONObject(response);
            return json.optString("text", "No text field found.");
        }
        return "Unable to fetch message text.";
    }

    private static String readResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return response.toString();
    }

    private static String generateRandomEmail() {
        Random random = new Random();
        StringBuilder email = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            email.append(random.nextInt(10));
        }

        for (int i = 0; i < 5; i++) {
            email.append((char) ('a' + random.nextInt(26)));
        }

        email.append("@chefalicious.com");
        return email.toString();
    }
}
