package com.malouane.udarecipes.ui.main;

import android.view.View;
import com.malouane.udarecipes.data.entity.Recipe;

interface RecipeListCallback {
  void onRecipeClicked(Recipe recipeEntity, View sharedView);
}
