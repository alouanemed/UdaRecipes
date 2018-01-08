package com.malouane.udarecipes.features.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.malouane.udarecipes.data.entity.Step;
import com.malouane.udarecipes.databinding.ItemStepBinding;
import com.malouane.udarecipes.features.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends BaseAdapter<StepsAdapter.StepViewHolder, Step> {

  List<Step> stepList;
  StepsListCallback callback;

  public StepsAdapter(StepsListCallback callback) {
    stepList = new ArrayList<>();
    this.callback = callback;
  }

  @Override public void setData(List<Step> steps) {
    this.stepList = steps;
    notifyDataSetChanged();
  }

  public void clearData() {
    stepList.clear();
  }

  @Override public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    return StepViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup);
  }

  @Override public void onBindViewHolder(StepViewHolder viewHolder, int i) {
    viewHolder.onBind(stepList.get(i));
  }

  @Override public int getItemCount() {
    return stepList.size();
  }

  static class StepViewHolder extends RecyclerView.ViewHolder {

    ItemStepBinding binding;

    public StepViewHolder(ItemStepBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public static StepViewHolder create(LayoutInflater inflater, ViewGroup parent) {
      ItemStepBinding inflate = ItemStepBinding.inflate(inflater, parent, false);
      return new StepViewHolder(inflate);
    }

    public void onBind(Step step) {
      binding.setStep(step);
      binding.executePendingBindings();
    }
  }
}
