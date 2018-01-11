package com.malouane.udarecipes.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.malouane.udarecipes.R;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.features.detail.RecipeDetailActivity;
import com.malouane.udarecipes.features.main.MainActivity;
import com.orhanobut.hawk.Hawk;

public class RecipeWidgetProvider extends AppWidgetProvider {

  void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

    Recipe recipe = Hawk.get(Recipe.KEY_RECIPE);

    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_list);
    if (recipe != null && recipe.getName() != null) {
      views.setTextViewText(R.id.recipe_name_tv, recipe.getName());
      Intent appIntent = new Intent(context, RecipeDetailActivity.class);
      Hawk.put(Recipe.KEY_RECIPE, recipe);

      TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
      stackBuilder.addParentStack(MainActivity.class);
      stackBuilder.addNextIntent(appIntent);

      PendingIntent appPendingIntent =
          stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

      views.setOnClickPendingIntent(R.id.widget_layout_main, appPendingIntent);
    } else {
      Intent appIntent = new Intent(context, MainActivity.class);
      PendingIntent appPendingIntent =
          PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      views.setOnClickPendingIntent(R.id.widget_layout_main, appPendingIntent);
    }

    Intent remoteAdapterIntent = new Intent(context, IngredientListWidgetService.class);
    views.setRemoteAdapter(R.id.widget_ingredient_list, remoteAdapterIntent);

    views.setEmptyView(R.id.widget_ingredient_list, R.id.empty_view);

    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

