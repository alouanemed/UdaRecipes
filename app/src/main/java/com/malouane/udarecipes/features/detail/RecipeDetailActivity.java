package com.malouane.udarecipes.features.detail;

import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.malouane.udarecipes.R;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.data.entity.Step;
import com.malouane.udarecipes.databinding.ActivityRecipeDetailsBinding;
import com.malouane.udarecipes.di.Injectable;
import com.malouane.udarecipes.features.step.StepDetailActivity;
import com.malouane.udarecipes.features.step.StepDetailFragment;
import com.orhanobut.hawk.Hawk;

public class RecipeDetailActivity extends AppCompatActivity
    implements StepsListCallback, Injectable {

  private static final String KEY_RECIPE_POSTER = "KEY_RECIPE_POSTER";
  LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
  ActivityRecipeDetailsBinding binding;
  Recipe recipe;
  private DetailsListFragment detailsListFragment;
  private StepDetailFragment stepDetailFragment;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    initFragments();
  }

  private void initFragments() {

    FragmentManager fm = getSupportFragmentManager();
    detailsListFragment = (DetailsListFragment) fm.findFragmentById(R.id.step_list_fragment);
    recipe = Hawk.get(Recipe.KEY_RECIPE);

    setTitle(recipe.getName());

    detailsListFragment.bindRecipe(recipe);

    stepDetailFragment = (StepDetailFragment) fm.findFragmentById(R.id.step_detail_fragment);

  }

  @Override public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }

  @Override public void onStepClicked(Step step) {

  }

  @Override public void onStepClickedWithPosition(Step step, int position) {
    if (stepDetailFragment != null && stepDetailFragment.isAdded()) {
      //has detail fragment
      Hawk.put(Step.KEY_STEP, step);
    } else {
      Hawk.put(StepDetailActivity.RECIPE_EXTRA, recipe);
      Hawk.put(StepDetailActivity.STEP_INDEX_EXTRA, position);

      this.startActivity(new Intent(this, StepDetailActivity.class));
    }
  }
}
