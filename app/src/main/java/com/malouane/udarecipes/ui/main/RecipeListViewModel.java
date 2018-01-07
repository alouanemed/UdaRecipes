package com.malouane.udarecipes.ui.main;

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

  private MutableLiveData<RecipeEntity> RecipesList;
  private DataManager dataManager;

  @Inject public RecipeListViewModel(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  public LiveData<RecipeEntity> getRecipesList(boolean shouldGetData) {
    if (RecipesList == null) {
      shouldGetData = true;
    } else {
      if (RecipesList.getValue() == null) shouldGetData = true;
    }

    if (shouldGetData) {
      RecipesList = new MutableLiveData<>();
      loadRecipesList(RecipesList);
    }

    return RecipesList;
  }

  private void loadRecipesList(MutableLiveData<RecipeEntity> RecipesList) {
    addDisposable(performGeRecipes().subscribe((response, throwable) -> {
      RecipeEntity recipesWrapper = new RecipeEntity();
      recipesWrapper.setRecipeEntities(response);
      RecipesList.setValue(recipesWrapper);
    }));
  }

  private Single<List<Recipe>> performGeRecipes() {
    return dataManager.performGetRecipes()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
