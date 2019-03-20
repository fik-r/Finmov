package com.fizus.mobiledev.finmov.di.modules;

import com.fizus.mobiledev.finmov.ui.detail.DetailMovieActivity;
import com.fizus.mobiledev.finmov.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract DetailMovieActivity bindDetailMovieActivity();
}