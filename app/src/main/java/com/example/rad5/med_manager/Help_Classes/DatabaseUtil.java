package com.example.rad5.med_manager.Help_Classes;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by akwa on 4/9/18.
 */

public class DatabaseUtil {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
