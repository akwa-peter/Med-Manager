package com.example.rad5.med_manager.Help_Classes;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akwa on 4/4/18.
 * This class collects the user information
 */

public class User {

    public String username;
    public String email;
    public String imgUrl;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String username, String email, String imgUrl) {
        this.username = username;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("email", email);
        result.put("imageUrl", imgUrl);
        return result;
    }

}
