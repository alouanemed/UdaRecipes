package com.malouane.udarecipes.features.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.data.entity.Step;
import com.malouane.udarecipes.databinding.FragmentDetailsListBinding;

public class DetailsListFragment extends Fragment implements StepsListCallback {

  private static final String INGREDIENT_CHECK_STATE = "StepListFragment_INGREDIENT_CHECK_STATE";

  FragmentDetailsListBinding binding;

  private IngredientsAdapter ingredientsAdapter;
  private StepsAdapter stepsAdapter;
  private boolean[] mIngredientSelection;
  private StepsListCallback callback;

  public DetailsListFragment() {
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentDetailsListBinding.inflate(inflater, container, false);

 /*   if (getActivity().getResources().getConfiguration().orientation
        == Configuration.ORIENTATION_PORTRAIT) {
      binding.recyclerViewRecipesList.setLayoutManager(new GridLayoutManager(getContext(), 2));
    } else {
      binding.recyclerViewRecipesList.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }*/

    binding.rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.rvSteps.setNestedScrollingEnabled(false);
    binding.rvIngredients.setNestedScrollingEnabled(false);

    ingredientsAdapter = new IngredientsAdapter();
    stepsAdapter = new StepsAdapter(this);

    binding.rvSteps.setAdapter(stepsAdapter);
    binding.rvIngredients.setAdapter(ingredientsAdapter);

    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey(INGREDIENT_CHECK_STATE)) {
        mIngredientSelection = savedInstanceState.getBooleanArray(INGREDIENT_CHECK_STATE);
      }
    }

    return binding.getRoot();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    try {
      callback = (StepsListCallback) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + " must implement StepsListCallback");
    }
  }

  @Override public void onStepClicked(Step step) {
  }

  @Override public void onStepClickedWithPosition(Step step, int position) {
    callback.onStepClickedWithPosition(step, position);
  }

  public void bindRecipe(Recipe recipe) {
    binding.setRecipe(recipe);
  }
}
