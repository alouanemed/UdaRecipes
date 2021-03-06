package com.malouane.udarecipes.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Recipe {

  public static final String KEY_RECIPE = "KEY_RECIPE";

  @SerializedName("id") @Expose private Integer id;
  @SerializedName("name") @Expose private String name;
  @SerializedName("ingredients") @Expose private List<Ingredient> ingredients = null;
  @SerializedName("steps") @Expose private List<Step> steps = null;
  @SerializedName("servings") @Expose private Integer servings;
  @SerializedName("image") @Expose private String image;

  public Recipe(Integer id, String name, List<Ingredient> ingredients, List<Step> steps,
      Integer servings, String image) {
    this.id = id;
    this.name = name;
    this.ingredients = ingredients;
    this.steps = steps;
    this.servings = servings;
    this.image = image;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public Integer getServings() {
    return servings;
  }

  public void setServings(Integer servings) {
    this.servings = servings;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
