package com.planitary.message.push.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpUtils {
    public static String getCurrentIPAddress() throws IOException {
        URL url = new URL("https://api.ipify.org/?format=json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            return jsonObject.get("ip").getAsString();
        }
        throw new IOException("Failed to fetch IP address");
    }
}
