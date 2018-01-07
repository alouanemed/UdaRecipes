package com.malouane.udarecipes.di;

import com.malouane.udarecipes.ui.main.RecipeListFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class FragmentBuildersModule {
  @ContributesAndroidInjector abstract RecipeListFragment listFragment();
}