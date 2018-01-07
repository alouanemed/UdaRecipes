package com.malouane.udarecipes.data.network;

import com.malouane.udarecipes.data.entity.Recipe;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;

public class RecipesHttp implements IRecipesHttp {

  ApiService mService;

  @Inject public RecipesHttp(ApiService mService) {
    this.mService = mService;
  }

  @Override public Single<List<Recipe>> performGetRecipes() {
    return mService.loadRecipes();
  }
}
