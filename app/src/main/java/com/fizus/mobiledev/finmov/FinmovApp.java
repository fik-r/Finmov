package com.fizus.mobiledev.finmov;

import com.fizus.mobiledev.finmov.di.components.AppComponent;
import com.fizus.mobiledev.finmov.di.components.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class FinmovApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
