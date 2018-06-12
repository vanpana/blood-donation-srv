package com.cyberschnitzel.Util;

import com.cyberschnitzel.Domain.Exceptions.ConfigException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FirebaseUtil {
    private final static int timeout = 10;
    private static boolean initialized = false;
    public final static String RAFFLES_GR_ID = "raffles";

    public static boolean sendAsyncNotification(String destinationToken) {
        if (!initialized) {
            initialized = initialise();

            if (!initialized) return false;
        }

        Message fireMessage = Message.builder()
                .putData("notification", "")
                .setToken(destinationToken)
                .build();

        new Thread(() -> {
            String response = null;
            try {
                response = FirebaseMessaging.getInstance().sendAsync(fireMessage).get(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
            System.out.println(response);
        }).start();

        return true;
    }

    private static boolean initialise() {
        try {
            FileInputStream serviceAccount = new FileInputStream(Config.getResourcesFile("firebase.json"));

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            return true;
        } catch (IOException | ConfigException e) {
            return false;
        }
    }
}
