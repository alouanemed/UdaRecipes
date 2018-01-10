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
import com.malouane.udarecipes.features.step.StepDetailActivity;
import com.malouane.udarecipes.features.step.StepDetailFragment;
import com.orhanobut.hawk.Hawk;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

public class RecipeDetailActivity extends AppCompatActivity implements StepsListCallback {

  private static final String KEY_RECIPE_POSTER = "KEY_RECIPE_POSTER";
  LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
  ActivityRecipeDetailsBinding binding;
  @Inject RecipeDetailViewModel recipeDetailViewModel;
  private DetailsListFragment detailsListFragment;
  private StepDetailFragment stepDetailFragment;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    if (Hawk.contains(Recipe.KEY_RECIPE)) {
      recipeDetailViewModel.setRecipe(Hawk.get(Recipe.KEY_RECIPE));
    }

    initFragments();
  }

  private void initFragments() {

    FragmentManager fm = getSupportFragmentManager();
    detailsListFragment = (DetailsListFragment) fm.findFragmentById(R.id.step_list_fragment);
    Recipe recipe = recipeDetailViewModel.getRecipe().getValue();

    setTitle(recipe.getName());

    detailsListFragment.bindRecipe(recipe);

    stepDetailFragment = (StepDetailFragment) fm.findFragmentById(R.id.step_detail_fragment);
    if (stepDetailFragment != null) {
      stepDetailFragment.setRecipe(recipe);
    }
  }

  @Override public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }

  @Override public void onStepClicked(Step step) {

  }

  @Override public void onStepClickedWithPosition(Step step, int position) {
    if (stepDetailFragment != null && stepDetailFragment.isAdded()) {
      //has detail fragment
      stepDetailFragment.bindStep(position);
    } else {
      Class destinationClass = StepDetailActivity.class;
      Intent intentToStartDetailActivity = new Intent(this, destinationClass);

      Hawk.put(StepDetailActivity.RECIPE_EXTRA, recipeDetailViewModel.getRecipe().getValue());
      Hawk.put(StepDetailActivity.STEP_INDEX_EXTRA, position);

      this.startActivity(intentToStartDetailActivity);
    }
  }
}