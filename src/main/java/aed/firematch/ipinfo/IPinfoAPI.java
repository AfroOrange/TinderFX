package aed.firematch.ipinfo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPinfoAPI {

    public static String getLocation() throws IOException {
        // Read the API key from the config file
        String apiKey;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/ipinfo.config"))) {
            apiKey = br.readLine();
        }

        String url = "https://ipinfo.io/json?token=" + apiKey;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(content.toString());

        return json.getString("city");
    }
}