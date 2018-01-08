package com.malouane.udarecipes.features.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.malouane.udarecipes.data.DataManager;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.vm.BaseViewModel;
import javax.inject.Inject;

public class RecipeDetailViewModel extends BaseViewModel {
  private DataManager dataManager;
  private MutableLiveData<Recipe> recipe;

  @Inject public RecipeDetailViewModel(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  public LiveData<Recipe> getRecipe() {
    return recipe;
  }

  void setRecipe(@NonNull Recipe recipe) {
    this.recipe = new MutableLiveData<>();
    this.recipe.setValue(recipe);
  }
}
