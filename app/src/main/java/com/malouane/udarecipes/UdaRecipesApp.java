package com.malouane.udarecipes;

import android.app.Activity;
import android.app.Application;
import com.malouane.udarecipes.di.DaggerAppComponent;
import com.orhanobut.hawk.Hawk;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Initialize dependency graph for our app using Dagger
 */

public class UdaRecipesApp extends Application implements HasActivityInjector {

  @Inject DispatchingAndroidInjector<Activity> activityDispatchingInjector;

  @Override public void onCreate() {
    super.onCreate();
    initializeComponent();
  }

  private void initializeComponent() {
    DaggerAppComponent.builder().application(this).build().inject(this);

    //init Timber
    Timber.plant(new Timber.DebugTree());

    Hawk.init(this).build();
  }

  @Override public AndroidInjector<Activity> activityInjector() {
    return activityDispatchingInjector;
  }
}
