package com.malouane.udarecipes.widget;

import android.app.Application;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.malouane.udarecipes.R;
import com.malouane.udarecipes.UdaRecipesApp;
import com.malouane.udarecipes.data.DataManager;
import com.malouane.udarecipes.data.entity.Ingredient;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.di.DaggerAppComponent;
import com.orhanobut.hawk.Hawk;
import javax.inject.Inject;

public class IngredientListWidgetService extends RemoteViewsService {

  @Override public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new GridRemoteViewsFactory(getApplication());
  }

  public class GridRemoteViewsFactory implements RemoteViewsFactory {

    Recipe recipe;

    @Inject DataManager dataManager;

    GridRemoteViewsFactory(Application application) {
      DaggerAppComponent.builder().application(application)
          .build()
          .inject((UdaRecipesApp) application);
    }

    @Override public void onCreate() {
    }

    @Override public void onDataSetChanged() {
      recipe = Hawk.get(Recipe.KEY_RECIPE);
    }

    @Override public void onDestroy() {

    }

    @Override public int getCount() {
      return recipe == null ? 0 : recipe.getIngredients().size();
    }

    @Override public RemoteViews getViewAt(int i) {
      if (recipe == null || recipe.getIngredients().size() < i) return null;
      Ingredient ingredient = recipe.getIngredients().get(i);

      RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_ingredient_item);

      views.setTextViewText(R.id.widget_ingredient_name_tv, ingredient.getIngredient());
      views.setTextViewText(R.id.widget_ingredient_quantity_tv,
          ingredient.getQuantity() + ingredient.getMeasure());

      Intent fillIntent = new Intent();
      Hawk.put(Recipe.KEY_RECIPE, recipe);
      views.setOnClickFillInIntent(R.id.widget_item_container, fillIntent);
      return views;
    }

    @Override public RemoteViews getLoadingView() {
      return null;
    }

    @Override public int getViewTypeCount() {
      return 1;
    }

    @Override public long getItemId(int i) {
      return i;
    }

    @Override public boolean hasStableIds() {
      return true;
    }
  }
}
