package com.malouane.udarecipes.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.malouane.udarecipes.ui.main.RecipeListViewModel;
import com.malouane.udarecipes.vm.AppViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module public abstract class ViewModelModule {

  @Binds @IntoMap @ViewModelKey(RecipeListViewModel.class)
  abstract ViewModel bindsMovieListViewModel(RecipeListViewModel recipeListViewModel);
/*
  @Binds @IntoMap @ViewModelKey(MovieDetailViewModel.class)
  abstract ViewModel bindsMovieDetailViewModel(MovieDetailViewModel movieDetailViewModel);*/

  @Binds abstract ViewModelProvider.Factory bindsViewModelFactory(
      AppViewModelFactory movisViewModelFactory);
}