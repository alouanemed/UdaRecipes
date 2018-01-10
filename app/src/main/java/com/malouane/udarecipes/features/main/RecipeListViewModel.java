package com.malouane.udarecipes.features.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.malouane.udarecipes.data.DataManager;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.data.entity.RecipeEntity;
import com.malouane.udarecipes.vm.BaseViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

public class RecipeListViewModel extends BaseViewModel {

  private MutableLiveData<RecipeEntity> recipesList;
  private DataManager dataManager;

  @Inject public RecipeListViewModel(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  public void setListIfAvailable(MutableLiveData<RecipeEntity> recipesList) {
    this.recipesList = recipesList;
  }

  public LiveData<RecipeEntity> getRecipesList(boolean shouldGetData) {
    if (recipesList == null) {
      shouldGetData = true;
    } else {
      if (recipesList.getValue() == null) shouldGetData = true;
    }

    if (shouldGetData) {
      recipesList = new MutableLiveData<>();
      loadRecipesList(recipesList);
    }

    return recipesList;
  }

  private void loadRecipesList(MutableLiveData<RecipeEntity> RecipesList) {
    addDisposable(performGeRecipes().subscribe((response, throwable) -> {
      RecipeEntity recipesWrapper = new RecipeEntity(response);
      RecipesList.setValue(recipesWrapper);
    }));
  }

  public Single<List<Recipe>> performGeRecipes() {
    return dataManager.performGetRecipes()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
