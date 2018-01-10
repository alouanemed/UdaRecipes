package com.malouane.udarecipes.data.entity;

import java.util.List;

public class RecipeEntity {
  private List<Recipe> recipeEntities;

  public RecipeEntity(List<Recipe> recipeEntities) {
    this.recipeEntities = recipeEntities;
  }

  public List<Recipe> getRecipeEntities() {
    return recipeEntities;
  }

  public void setRecipeEntities(List<Recipe> recipeEntities) {
    this.recipeEntities = recipeEntities;
  }
}
