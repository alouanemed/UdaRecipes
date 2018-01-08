package com.malouane.udarecipes.features.main;

import android.view.View;
import com.malouane.udarecipes.data.entity.Recipe;

interface RecipeListCallback {
  void onRecipeClicked(Recipe recipeEntity, View sharedView);
}
