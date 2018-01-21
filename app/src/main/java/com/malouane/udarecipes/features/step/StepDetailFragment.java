package com.malouane.udarecipes.features.step;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.malouane.udarecipes.R;
import com.malouane.udarecipes.data.entity.Step;
import com.malouane.udarecipes.databinding.FragmentStepDetailBinding;
import com.malouane.udarecipes.utils.ExoPlayerHelper;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;

public class StepDetailFragment extends Fragment {

  private static final String CURRENT_POS_STATE = "CURRENT_POS_STATE";
  private static final String PLAY_STATE = "PLAY_STATE";
  private final String BUNDLE_KEY_RESUME_WINDOW = "resume_window";
  public boolean isFullMode = false;
  FragmentStepDetailBinding binding;
  boolean isPlayWhenReady;
  private Step step;
  private onStepNavigation listener;
  private ExoPlayerHelper playerHelper;
  private SetStepIndexHandler mSetStepIndexHandler;
  private int mStepIndex = -1;
  private SimpleExoPlayerView mPlayerView;

  private void initialize(@Nullable Bundle savedInstanceState) {
    playerHelper = new ExoPlayerHelper(getActivity());
    mPlayerView = binding.pvExoPlayer;

    initializeViews(savedInstanceState);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      listener = (onStepNavigation) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() + " must implement onStepNavigation");
    }
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentStepDetailBinding.inflate(inflater, container, false);
    initialize(savedInstanceState);
    return binding.getRoot();
  }

  private void initializeViews(@Nullable Bundle savedInstanceState) {
    if (savedInstanceState != null) {

      if (mSetStepIndexHandler != null) {
        mSetStepIndexHandler.handleSetStepIndex(Hawk.get(Step.STEP_INDEX_EXTRA));
      }

      int resumeWindow = savedInstanceState.getInt(BUNDLE_KEY_RESUME_WINDOW);
      long resumePosition = savedInstanceState.getLong(CURRENT_POS_STATE);
      playerHelper.setExoPlayerResumePosition(resumeWindow, resumePosition);
      initializeViewContents();
      controlFullModeAccordingToOrientation();
    } else {
      exitFullMode();
      playerHelper.clearExoPlayerResumePosition();
      initializeViewContents();
    }
    step = Hawk.get(Step.KEY_STEP);

    if (step != null) bindStep(step);

    binding.bNext.setOnClickListener(view -> listener.onNextButtonClicked());
    binding.bPrev.setOnClickListener(view -> listener.onPrevButtonClicked());
  }

  public void bindStep(Step s) {
    binding.setStep(s);
    step = s;
    setupExoPlayerView();
  }

  private void controlFullModeAccordingToOrientation() {
    if (mPlayerView.getVisibility() == View.VISIBLE) {
      int currentOrientation = getResources().getConfiguration().orientation;
      if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
        enterFullMode();
      } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) exitFullMode();
    }
  }

  public void exitFullMode() {
    if (!isFullMode) return;
    isFullMode = false;
    setExoPlayerViewToNormal();
    showAllViews();
    setWindowToNormal();
  }

  private void setExoPlayerViewToNormal() {
    setVideoSizeToNormal();
    setVideoBackgroundToNormal();
  }

  private void setVideoSizeToNormal() {
    ViewGroup.LayoutParams params = binding.pvExoPlayerContainer.getLayoutParams();
    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
    binding.pvExoPlayerContainer.setLayoutParams(params);
  }

  private void setVideoBackgroundToNormal() {
    TypedArray array = getActivity().getTheme().obtainStyledAttributes(new int[] {
        android.R.attr.colorBackground,
    });
    int backgroundColor = array.getColor(0, 0xFF00FF);
    binding.pvExoPlayerContainer.setBackgroundColor(backgroundColor);
    array.recycle();
  }

  private void showAllViews() {
    binding.tvShortDescription.setVisibility(View.VISIBLE);
    binding.bNext.setVisibility(View.VISIBLE);
    binding.bPrev.setVisibility(View.VISIBLE);
  }

  private void setWindowToNormal() {
    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    ((AppCompatActivity) getActivity()).getSupportActionBar().show();
  }

  private void initializeViewContents() {
    if (step != null) {
      setupExoPlayerView();
    }
  }

  private void setupExoPlayerView() {
    playerHelper.releaseExoPlayer();
    if (TextUtils.isEmpty(step.getVideoURL())) {
      showVideoEmptyView();
    } else {
      showExoPlayerView();
    }
  }

  private void showVideoEmptyView() {
    mPlayerView.setVisibility(View.GONE);
    binding.ivThumbnail.setVisibility(View.VISIBLE);
    binding.pvExoPlayer.setVisibility(View.GONE);
    if (!TextUtils.isEmpty(step.getThumbnailURL())) {
      Picasso.with(getActivity())
          .load(step.getThumbnailURL())
          .placeholder(R.drawable.ic_launcher_background)
          .error(R.drawable.err)
          .into(binding.ivThumbnail);
    }
  }

  private void showExoPlayerView() {
    initializeExoPlayerView();
    binding.ivThumbnail.setVisibility(View.GONE);
    binding.pvExoPlayer.setVisibility(View.VISIBLE);
  }

  private void initializeExoPlayerView() {
/*    if (playerHelper == null)
      playerHelper = new ExoPlayerHelper(getActivity());*/

    SimpleExoPlayer exoPlayer = playerHelper.getInitializedExoPlayer(step);
    mPlayerView.setPlayer(exoPlayer);
  }

  @Override public void onPause() {
    super.onPause();
    playerHelper.pauseExoPlayer();
  }

  @Override public void onStop() {
    super.onStop();
    playerHelper.updateResumePositionAndReleaseExoPlayer();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    if (mPlayerView.getVisibility() == View.VISIBLE) {
      playerHelper.updateExoPlayerResumePosition();
      outState.putInt(BUNDLE_KEY_RESUME_WINDOW, playerHelper.exoResumeWindow);
      outState.putLong(CURRENT_POS_STATE, playerHelper.exoResumePosition);
    }
  }

  @Override public void onStart() {
    super.onStart();
    if (needToReloadExoPlayer()) {
      exitFullMode();
      //initializeExoPlayerView();
    }
  }

  private boolean needToReloadExoPlayer() {
    return (playerHelper.getExoPlayer() == null) && (mPlayerView.getVisibility() == View.VISIBLE);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    playerHelper.clearExoPlayerResumePosition();
  }

  private void enterFullMode() {
    if (isFullMode) return;
    isFullMode = true;
    setExoPlayerViewToFull();
    hideAllViewsExceptVideo();
    setWindowToFullScreen();
  }

  private void setExoPlayerViewToFull() {
    setVideoSizeMatchParent();
    setVideoBackgroundToBlack();
  }

  private void setVideoSizeMatchParent() {
    ViewGroup.LayoutParams params = binding.pvExoPlayer.getLayoutParams();
    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
    binding.pvExoPlayer.setLayoutParams(params);
  }

  private void setVideoBackgroundToBlack() {
    binding.pvExoPlayerContainer.setBackgroundColor(Color.BLACK);
  }

  private void hideAllViewsExceptVideo() {
    binding.tvShortDescription.setVisibility(View.GONE);
    binding.tvStepDescription.setVisibility(View.GONE);
    binding.bPrev.setVisibility(View.GONE);
    binding.bNext.setVisibility(View.GONE);
  }

  private void setWindowToFullScreen() {
    getActivity().getWindow()
        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
  }

  public interface onStepNavigation {
    void onPrevButtonClicked();

    void onNextButtonClicked();
  }

  public interface SetStepIndexHandler {
    void handleSetStepIndex(int index);
  }
}
