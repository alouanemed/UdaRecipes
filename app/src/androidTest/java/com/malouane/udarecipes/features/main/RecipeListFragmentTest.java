package com.malouane.udarecipes.features.main;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import com.malouane.udarecipes.R;
import com.malouane.udarecipes.Utils.EspressoTestUtil;
import com.malouane.udarecipes.Utils.RecyclerViewMatcher;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.data.entity.RecipeEntity;
import com.malouane.udarecipes.features.detail.RecipeDetailActivity;
import com.malouane.udarecipes.testing.SingleFragmentActivity;
import com.malouane.udarecipes.utils.UdaRecipesUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.malouane.udarecipes.utils.UdaRecipesUtils.generateIngredients;
import static com.malouane.udarecipes.utils.UdaRecipesUtils.generateSteps;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class RecipeListFragmentTest {
  @Rule public ActivityTestRule<SingleFragmentActivity> activityRule =
      new ActivityTestRule<>(SingleFragmentActivity.class, true, true);

  private RecipeListViewModel viewModel;
  private MutableLiveData<RecipeEntity> recipesList = new MutableLiveData<>();

  @Before public void setUp() {
    EspressoTestUtil.disableProgressBarAnimations(activityRule);
    RecipeListFragment fragment = RecipeListFragment.newInstance();
    viewModel = mock(RecipeListViewModel.class);

    RecipeEntity recipeEntity = new RecipeEntity(getRecipeList());
    recipesList.postValue(recipeEntity);

    when(viewModel.getRecipesList(false)).thenReturn(recipesList);

    fragment.recipeListViewModel = viewModel;
    activityRule.getActivity().setFragment(fragment);
  }

  @Test public void loadDataList() {

    List<Recipe> recipes = setRecipes(4);

    for (int pos = 0; pos < recipes.size(); pos++) {
      Recipe recipe = recipes.get(pos);
      onView(listMatcher().atPosition(pos)).check(
          matches(hasDescendant(withText(recipe.getName()))));
    }

    Recipe recipe = recipes.get(1);

    onView(listMatcher().atPosition(1)).check(matches(hasDescendant(withText(recipe.getName()))));
  }

  @Test public void onClick() {
    Recipe clickedRecipe = setRecipes(2).get(1);
    Intents.init();
    onView(withText(clickedRecipe.getName())).perform(click());
    intended(hasComponent(RecipeDetailActivity.class.getName()));
    Intents.release();
  }

  @NonNull private RecyclerViewMatcher listMatcher() {
    return new RecyclerViewMatcher(R.id.recyclerView_recipes_list);
  }

  public List<Recipe> getRecipeList() {
    return UdaRecipesUtils.generateRecipeList();
  }

  private List<Recipe> setRecipes(int count) {
    List<Recipe> recipes = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      recipes.add(
          UdaRecipesUtils.createRecipe(i, "name" + i, generateIngredients(), generateSteps(), 3 + i,
              "image"));
    }

    recipesList.postValue(new RecipeEntity(recipes));
    return recipes;
  }
}
