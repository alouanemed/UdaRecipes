package com.malouane.udarecipes.features.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.malouane.udarecipes.data.entity.Ingredient;
import com.malouane.udarecipes.databinding.ItemIngredientBinding;
import com.malouane.udarecipes.features.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter
    extends BaseAdapter<IngredientsAdapter.IngredientViewHolder, Ingredient> {

  List<Ingredient> ingredientList;
  StepsListCallback callback;

  public IngredientsAdapter() {
    this.callback = callback;
    ingredientList = new ArrayList<>();
  }

  @Override public void setData(List<Ingredient> reviews) {
    this.ingredientList = reviews;
    notifyDataSetChanged();
  }

  public void clearData() {
    ingredientList.clear();
  }

  @Override
  public IngredientsAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    return IngredientsAdapter.IngredientViewHolder.create(
        LayoutInflater.from(viewGroup.getContext()), viewGroup, callback);
  }

  @Override
  public void onBindViewHolder(IngredientsAdapter.IngredientViewHolder viewHolder, int i) {
    viewHolder.onBind(ingredientList.get(i));
  }

  @Override public int getItemCount() {
    return ingredientList.size();
  }

  static class IngredientViewHolder extends RecyclerView.ViewHolder {

    ItemIngredientBinding binding;

    public IngredientViewHolder(ItemIngredientBinding binding, StepsListCallback callback) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public static IngredientsAdapter.IngredientViewHolder create(LayoutInflater inflater,
        ViewGroup parent, StepsListCallback callback) {
      ItemIngredientBinding inflate = ItemIngredientBinding.inflate(inflater, parent, false);
      return new IngredientsAdapter.IngredientViewHolder(inflate, callback);
    }

    public void onBind(Ingredient entity) {
      binding.setIngredient(entity);
      binding.executePendingBindings();
    }
  }
}