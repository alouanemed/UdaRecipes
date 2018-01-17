package com.malouane.udarecipes.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {

  public static final String KEY_STEP = "KEY_STEP";
  public static final String STEP_INDEX_EXTRA = "StepDetailActivity_STEP_INDEX_EXTRA";
  @SerializedName("id") @Expose private Integer id;
  @SerializedName("shortDescription") @Expose private String shortDescription;
  @SerializedName("description") @Expose private String description;
  @SerializedName("videoURL") @Expose private String videoURL;
  @SerializedName("thumbnailURL") @Expose private String thumbnailURL;
  public Step(Integer id, String shortDescription, String description, String videoURL,
      String thumbnailURL) {
    this.id = id;
    this.shortDescription = shortDescription;
    this.description = description;
    this.videoURL = videoURL;
    this.thumbnailURL = thumbnailURL;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVideoURL() {
    return videoURL;
  }

  public void setVideoURL(String videoURL) {
    this.videoURL = videoURL;
  }

  public String getThumbnailURL() {
    return thumbnailURL;
  }

  public void setThumbnailURL(String thumbnailURL) {
    this.thumbnailURL = thumbnailURL;
  }
}
