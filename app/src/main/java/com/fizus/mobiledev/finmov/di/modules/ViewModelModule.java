package com.fizus.mobiledev.finmov.di.modules;

import com.fizus.mobiledev.finmov.di.ViewModelFactory;
import com.fizus.mobiledev.finmov.di.ViewModelKey;
import com.fizus.mobiledev.finmov.ui.detail.DetailMovieViewModel;
import com.fizus.mobiledev.finmov.ui.main.MainViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailMovieViewModel.class)
    abstract ViewModel bindDetailMovieViewModel(DetailMovieViewModel detailMovieViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}
