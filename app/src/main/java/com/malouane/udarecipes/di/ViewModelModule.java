package com.malouane.udarecipes.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.malouane.udarecipes.features.main.RecipeListViewModel;
import com.malouane.udarecipes.vm.AppViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module public abstract class ViewModelModule {

  @Binds @IntoMap @ViewModelKey(RecipeListViewModel.class)
  abstract ViewModel bindsMovieListViewModel(RecipeListViewModel recipeListViewModel);
/*
  @Binds @IntoMap @ViewModelKey(RecipeDetailViewModel.class)
  abstract ViewModel bindsMovieDetailViewModel(RecipeDetailViewModel movieDetailViewModel);*/

  @Binds abstract ViewModelProvider.Factory bindsViewModelFactory(
      AppViewModelFactory movisViewModelFactory);
}