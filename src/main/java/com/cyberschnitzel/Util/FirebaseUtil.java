package com.cyberschnitzel.Util;

import com.cyberschnitzel.Domain.Exceptions.ConfigException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseUtil {
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
