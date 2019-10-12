package com.lyg.tools.joke;

import com.squareup.otto.Bus;

public class AppBus extends Bus {
    public static AppBus instance = new AppBus();
    private AppBus() {

    }
}
