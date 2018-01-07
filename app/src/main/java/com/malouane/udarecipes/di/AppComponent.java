package com.malouane.udarecipes.di;

import android.app.Application;
import com.malouane.udarecipes.UdaRecipesApp;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    AppModule.class, AndroidInjectionModule.class, ActivityBuilderModule.class
}) public interface AppComponent {

  void inject(UdaRecipesApp app);

  @Component.Builder interface Builder {
    @BindsInstance Builder application(Application application);

    AppComponent build();
  }
}
