package com.malouane.udarecipes.features.step;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.malouane.udarecipes.R;
import com.malouane.udarecipes.data.entity.Recipe;
import com.orhanobut.hawk.Hawk;
import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity
    implements StepDetailFragment.SetStepIndexHandler {

  public static final String RECIPE_EXTRA = "StepDetailActivity_RECIPE_EXTRA";
  public static final String STEP_INDEX_EXTRA = "StepDetailActivity_STEP_INDEX_EXTRA";

  public static final String STEP_INDEX_STATE = "StepDetailActivity_STEP_INDEX_STATE";
  private Recipe recipe;
  private int mStepIndex;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_step_detail);

    assert getSupportActionBar() != null;
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    FragmentManager fm = getSupportFragmentManager();
    StepDetailFragment stepDetailFragment =
        (StepDetailFragment) fm.findFragmentById(R.id.step_detail_fragment);

    if (Hawk.contains(RECIPE_EXTRA)) {
      recipe = Hawk.get(RECIPE_EXTRA);
    }
    if (Hawk.contains(STEP_INDEX_EXTRA)) {
      mStepIndex = Hawk.get(STEP_INDEX_EXTRA, 0);
    }

    if (recipe == null || recipe.getSteps() == null || mStepIndex >= recipe.getSteps().size()) {
      Timber.e("ERRR");
      return;
    }

    stepDetailFragment.setRecipe(recipe);
    stepDetailFragment.bindStep(mStepIndex);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putInt(STEP_INDEX_STATE, mStepIndex);
  }

  @Override public void handleSetStepIndex(int index) {
    String titleTemplate = getString(R.string.step_detail_title);
    mStepIndex = index;
    setTitle(String.format(titleTemplate, String.valueOf(index + 1)));
  }
}