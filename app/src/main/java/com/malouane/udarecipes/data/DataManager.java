package com.malouane.udarecipes.data;

import android.app.Application;
import android.support.annotation.NonNull;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.data.network.IRecipesHttp;
import io.reactivex.Single;
import java.util.List;

public class DataManager implements IRecipesHttp {

  IRecipesHttp recipesHttp;
  Application app;

  public DataManager(IRecipesHttp recipesHttp, @NonNull final Application app) {
    this.recipesHttp = recipesHttp;
    this.app = app;
  }

  @Override public Single<List<Recipe>> performGetRecipes() {
    return recipesHttp.performGetRecipes();
  }
}
