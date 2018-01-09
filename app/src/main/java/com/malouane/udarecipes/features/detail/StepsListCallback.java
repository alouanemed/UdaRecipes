package com.malouane.udarecipes.features.detail;

import com.malouane.udarecipes.data.entity.Step;

interface StepsListCallback {
  void onStepClicked(Step step);

  void onStepClickedWithPosition(Step step, int position);
}