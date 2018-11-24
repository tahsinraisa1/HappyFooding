package com.example.hp.happyfooding;

import android.app.Application;

import io.smooch.core.Settings;
import io.smooch.core.Smooch;

public class CustomUiApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Smooch.init(this, new Settings("5bf9a118d8c85b0022e974ff"), null);
    }
}
