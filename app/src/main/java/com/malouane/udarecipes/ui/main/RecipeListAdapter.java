package com.malouane.udarecipes.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.databinding.ItemRecipeBinding;
import com.malouane.udarecipes.ui.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends BaseAdapter<RecipeListAdapter.RecipeViewHolder, Recipe> {

  private final RecipeListCallback RecipeListCallback;
  private List<Recipe> RecipeEntities;

  public RecipeListAdapter(@NonNull RecipeListCallback RecipeListCallback) {
    RecipeEntities = new ArrayList<>();
    this.RecipeListCallback = RecipeListCallback;
  }

  @Override public void setData(List<Recipe> RecipeEntities) {
    this.RecipeEntities = RecipeEntities;
    notifyDataSetChanged();
  }

  public void clearData() {
    RecipeEntities.clear();
  }

  @Override public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    return RecipeViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup,
        RecipeListCallback);
  }

  @Override public void onBindViewHolder(RecipeViewHolder viewHolder, int i) {
    viewHolder.onBind(RecipeEntities.get(i));
  }

  @Override public int getItemCount() {
    return RecipeEntities.size();
  }

  static class RecipeViewHolder extends RecyclerView.ViewHolder {

    ItemRecipeBinding binding;

    public RecipeViewHolder(ItemRecipeBinding binding, RecipeListCallback callback) {
      super(binding.getRoot());
      this.binding = binding;
      binding.getRoot()
          .setOnClickListener(v -> callback.onRecipeClicked(binding.getRecipe(), binding.ivCover));
    }

    public static RecipeViewHolder create(LayoutInflater inflater, ViewGroup parent,
        RecipeListCallback callback) {
      ItemRecipeBinding inflate = ItemRecipeBinding.inflate(inflater, parent, false);
      return new RecipeViewHolder(inflate, callback);
    }

    public void onBind(Recipe recipe) {
      if (recipe.getImage().isEmpty()) {
        recipe.setImage("https://source.unsplash.com/800x1000/?" + recipe.getName());
      }
      binding.setRecipe(recipe);
      binding.executePendingBindings();
    }
  }
}
