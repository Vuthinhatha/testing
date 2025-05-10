package Utils;

import java.util.Random;
import java.util.regex.Matcher;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import Utils.TempMailUtils;

public class TempMailUtils {
    public static String generateRandomEmail() {
        Random random = new Random();
        StringBuilder email = new StringBuilder();
        for (int i = 0; i < 3; i++)
            email.append(random.nextInt(10));
        for (int i = 0; i < 5; i++)
            email.append((char) ('a' + random.nextInt(26)));
        return email.append("@chefalicious.com").toString();
    }

    public static void createAccount(String email, String password) throws IOException {
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

    public static String getToken(String email, String password) throws IOException {
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

    public static String waitForMessageId(String token) throws IOException, InterruptedException {
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

    public static String getMessageText(String token, String messageId) throws IOException {
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

    public static String extractCode(String text) {
        Pattern pattern = Pattern.compile("vui lòng nhập mã gồm\\s*6 chữ số trên Trang xác minh Email:\\s*(\\d{6})");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null; // or throw exception if preferred
    }

    public static String readResponse(HttpURLConnection con) throws IOException {
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
