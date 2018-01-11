package com.malouane.udarecipes;

import android.app.Application;
import com.orhanobut.hawk.Hawk;

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See @{@link UdaRecipesTestRunner}
 */
public class UdaRecipesTestApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    Hawk.init(this).build();
  }
}
