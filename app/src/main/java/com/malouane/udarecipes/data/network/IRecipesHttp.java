package com.malouane.udarecipes.data.network;

import com.malouane.udarecipes.data.entity.Recipe;
import io.reactivex.Single;
import java.util.List;

public interface IRecipesHttp {
  Single<List<Recipe>> performGetRecipes();
}
