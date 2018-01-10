package com.malouane.udarecipes.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.malouane.udarecipes.data.entity.Ingredient;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.data.entity.Step;
import java.util.ArrayList;
import java.util.List;

public class UdaRecipesUtils {

  public static String buildYouTubeLink(@NonNull final String key) {
    final Uri.Builder builder = Uri.parse("https://www.youtube.com/watch").buildUpon();
    builder.appendQueryParameter("v", key);
    return builder.build().toString();
  }

  public static void shareTrailer(Context ctx, String ytKey) {
    Intent share = new Intent(Intent.ACTION_SEND);
    share.setType("text/plain");
    share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    share.putExtra(Intent.EXTRA_SUBJECT, "Check out this Trailer, You May Like it !");
    share.putExtra(Intent.EXTRA_TEXT, buildYouTubeLink(ytKey));
    ctx.startActivity(Intent.createChooser(share, "Share Trailer"));
  }

  public static void openTrailer(final Context context, @NonNull final String link) {
    Intent share = new Intent(Intent.ACTION_VIEW);
    share.setData(Uri.parse(link));
    context.startActivity(share);
  }

  public static List<Ingredient> generateIngredients() {
    List<Ingredient> ingredients = new ArrayList<>();

    for (int i = 0; i < 5; i++)
      ingredients.add(new Ingredient(i + 3, "measure" + i, "ingredient"));

    return ingredients;
  }

  public static List<Step> generateSteps() {
    List<Step> steps = new ArrayList<>();

    for (int i = 0; i < 5; i++)
      steps.add(new Step(i, "shortDescription" + i, "description", "videoURL", "thumbnailURL"));

    return steps;
  }

  public static List<Recipe> generateRecipeList() {
    List<Recipe> list = new ArrayList<>();
    for (int i = 0; i < 5; i++)
      createRecipe(i, "name" + i, generateIngredients(), generateSteps(), 3, "image");
    return list;
  }

  public static Recipe createRecipe(Integer id, String name, List<Ingredient> ingredients,
      List<Step> steps, Integer servings, String image) {
    return new Recipe(id, name, ingredients, steps, servings, image);
  }
}
