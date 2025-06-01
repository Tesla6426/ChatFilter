package net.txsla.chatfilter.log;

import net.txsla.chatfilter.config;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

public class webhook {
    public static boolean enabled;
    public static String webhook;
    public static void add(String message){
        // do not run if not enabled
        if (!enabled) return;

        try {
            if (config.debug) System.out.println("Sending Webhook");

            // declare URL
            URL url = new URL(webhook);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // add properties
            con.addRequestProperty("User-Agent", "Mozilla");
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String jsonPayload = String.format("{\"content\":\"%s\"}", Matcher.quoteReplacement(message));

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // response code for debug
            int responseCode = con.getResponseCode();
            if (config.debug) {
                if (responseCode < 200 || responseCode >= 300)
                    System.err.println("Webhook request failed: " + responseCode);
            }

            con.disconnect();
        } catch (Exception e) {
        if (config.debug) System.out.println("Unable to load Webhook" + e);
        }
    }
}
