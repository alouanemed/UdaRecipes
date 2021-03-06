package com.malouane.udarecipes.features.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.malouane.udarecipes.R;
import com.malouane.udarecipes.databinding.ActivityMainBinding;
import com.malouane.udarecipes.testing.UdaIdlingResource;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  @Inject DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;
  UdaIdlingResource idlingResource;

  ActivityMainBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    getIdlingResource().setIsIdle(false);
  }

  @Override public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentAndroidInjector;
  }

  @VisibleForTesting @NonNull public UdaIdlingResource getIdlingResource() {
    if (idlingResource == null) {
      idlingResource = new UdaIdlingResource();
    }
    return idlingResource;
  }

}