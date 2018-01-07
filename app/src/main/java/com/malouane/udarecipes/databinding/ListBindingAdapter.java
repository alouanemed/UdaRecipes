package com.malouane.udarecipes.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import com.malouane.udarecipes.data.entity.RecipeEntity;
import com.malouane.udarecipes.ui.BaseAdapter;

public final class ListBindingAdapter {
  @BindingAdapter({ "bind:data" })
  public static void setItems(RecyclerView recyclerView, RecipeEntity recipeEntity) {
    RecyclerView.Adapter adapter = recyclerView.getAdapter();
    if (adapter == null) return;

    if (recipeEntity == null || recipeEntity.getRecipeEntities() == null) return;

    if (adapter instanceof BaseAdapter) {
      ((BaseAdapter) adapter).setData(recipeEntity.getRecipeEntities());
      adapter.notifyDataSetChanged();
    }
  }
}
