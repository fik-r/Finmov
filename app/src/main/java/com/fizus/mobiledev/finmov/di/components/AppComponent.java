package com.fizus.mobiledev.finmov.di.components;

import android.app.Application;

import com.fizus.mobiledev.finmov.FinmovApp;
import com.fizus.mobiledev.finmov.di.modules.ActivityBindingModule;
import com.fizus.mobiledev.finmov.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBindingModule.class
})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(FinmovApp finmovApp);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
