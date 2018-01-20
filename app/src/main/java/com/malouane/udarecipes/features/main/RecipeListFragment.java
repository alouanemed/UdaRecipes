package com.malouane.udarecipes.features.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.databinding.FragmentRecipeListBinding;
import com.malouane.udarecipes.di.Injectable;
import com.malouane.udarecipes.features.detail.RecipeDetailActivity;
import com.orhanobut.hawk.Hawk;
import javax.inject.Inject;

public class RecipeListFragment extends Fragment
    implements Injectable, RecipeListCallback {

  @Inject RecipeListViewModel recipeListViewModel;

  private FragmentRecipeListBinding binding;
  private RecipeListAdapter adapter;

  public static RecipeListFragment newInstance() {
    return new RecipeListFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentRecipeListBinding.inflate(inflater, container, false);
    if (getActivity().getResources().getConfiguration().orientation
        == Configuration.ORIENTATION_PORTRAIT) {
      binding.recyclerViewRecipesList.setLayoutManager(new GridLayoutManager(getContext(), 2));
    } else {
      binding.recyclerViewRecipesList.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }
    adapter = new RecipeListAdapter(this);
    binding.recyclerViewRecipesList.setAdapter(adapter);
    return binding.getRoot();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getRecipes(false);
  }

  private void getRecipes(boolean forceUpdate) {
    hideLoading(false);
    recipeListViewModel.getRecipesList(forceUpdate).observe(this, listResource -> {
      hideLoading(true);
      binding.setRecipesList(listResource);
    });
  }

  private void hideLoading(boolean v) {
    binding.recyclerViewRecipesList.setVisibility(v ? View.VISIBLE : View.GONE);
    binding.pbLoading.setVisibility(v ? View.GONE : View.VISIBLE);
  }

  private void clearList() {
    adapter.clearData();
  }

  @Override public void onRecipeClicked(Recipe recipeEntity, View sharedView) {
    Hawk.put(Recipe.KEY_RECIPE, recipeEntity);
    startActivity(new Intent(getActivity(), RecipeDetailActivity.class));
  }
}