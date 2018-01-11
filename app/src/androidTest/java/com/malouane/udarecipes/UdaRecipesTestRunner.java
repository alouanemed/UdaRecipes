package com.malouane.udarecipes;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Custom runner to disable dependency injection.
 */
public class UdaRecipesTestRunner extends AndroidJUnitRunner {
  @Override public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return super.newApplication(cl, UdaRecipesTestApp.class.getName(), context);
  }
}
