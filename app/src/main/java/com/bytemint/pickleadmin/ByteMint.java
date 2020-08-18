package com.bytemint.pickleadmin;

import android.app.Application;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.FirebaseDatabase;

public class ByteMint extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
