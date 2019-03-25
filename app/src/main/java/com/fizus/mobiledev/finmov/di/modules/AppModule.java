package com.fizus.mobiledev.finmov.di.modules;

import android.app.Application;
import android.content.Context;

import com.fizus.mobiledev.finmov.data.network.ApiHelper;
import com.fizus.mobiledev.finmov.data.network.ApiHelperImpl;
import com.fizus.mobiledev.finmov.utils.rx.SchedulerProvider;
import com.fizus.mobiledev.finmov.utils.rx.SchedulerProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module(includes = {ViewModelModule.class, NetworkModule.class, DatabaseModule.class})
public abstract class AppModule {

    @Binds
    abstract Context bindContext(Application application);

    @Binds
    abstract SchedulerProvider bindSchedulerProvider(SchedulerProviderImpl schedulerProviderImpl);

    @Binds
    abstract ApiHelper bindApiHelper(ApiHelperImpl apiHelperImpl);
}
