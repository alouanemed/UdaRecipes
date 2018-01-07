package com.malouane.udarecipes.data.network;

import com.malouane.udarecipes.data.entity.Recipe;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;

public interface ApiService {
  @GET("baking.json") Single<List<Recipe>> loadRecipes();
}