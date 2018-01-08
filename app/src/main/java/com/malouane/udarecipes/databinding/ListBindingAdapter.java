package com.malouane.udarecipes.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import com.malouane.udarecipes.data.entity.Ingredient;
import com.malouane.udarecipes.data.entity.RecipeEntity;
import com.malouane.udarecipes.data.entity.Step;
import com.malouane.udarecipes.features.BaseAdapter;
import java.util.List;

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

  @BindingAdapter({ "bind:steps" })
  public static void setSteps(RecyclerView recyclerView, List<Step> data) {
    RecyclerView.Adapter adapter = recyclerView.getAdapter();
    if (adapter == null) return;

    if (data == null) return;

    if (adapter instanceof BaseAdapter) {
      ((BaseAdapter) adapter).setData(data);
      adapter.notifyDataSetChanged();
    }
  }

  @BindingAdapter({ "bind:ingredients" })
  public static void setIngredient(RecyclerView recyclerView, List<Ingredient> data) {
    RecyclerView.Adapter adapter = recyclerView.getAdapter();
    if (adapter == null) return;

    if (data == null) return;

    if (adapter instanceof BaseAdapter) {
      ((BaseAdapter) adapter).setData(data);
      adapter.notifyDataSetChanged();
    }
  }


}
