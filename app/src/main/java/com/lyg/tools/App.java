package com.lyg.tools;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.raizlabs.android.dbflow.config.FlowManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        FlowManager.init(this);
    }
}
