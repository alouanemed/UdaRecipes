package com.malouane.udarecipes.di;

import com.malouane.udarecipes.ui.main.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class ActivityBuilderModule {

  @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
  abstract MainActivity mainActivity();

  //@ContributesAndroidInjector abstract RecipeDetailActivity movieDetailActivity();
}
