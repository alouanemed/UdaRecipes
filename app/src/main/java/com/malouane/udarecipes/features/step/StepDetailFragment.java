package com.malouane.udarecipes.features.step;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.malouane.udarecipes.BuildConfig;
import com.malouane.udarecipes.R;
import com.malouane.udarecipes.data.entity.Recipe;
import com.malouane.udarecipes.data.entity.Step;
import com.malouane.udarecipes.databinding.FragmentStepDetailBinding;
import com.squareup.picasso.Picasso;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {

  private static final String NOW_PLAYING_STATE = "NOW_PLAYING_STATE";
  private static SimpleExoPlayer mExoPlayer;
  private static MediaSessionCompat mMediaSession;
  FragmentStepDetailBinding binding;
  private Recipe recipe;
  //this is so it does not collide with index 0
  private int mStepIndex = -1;
  private Context mContext;
  private SetStepIndexHandler mSetStepIndexHandler;
  private View mRootView;
  private SimpleExoPlayerView mPlayerView;
  private PlaybackStateCompat.Builder mStateBuilder;

  private String mNowPlaying = "";
  private boolean mSkipOneExoBind = false;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentStepDetailBinding.inflate(inflater, container, false);
    mContext = getContext();

    if (mContext instanceof SetStepIndexHandler) {
      mSetStepIndexHandler = (SetStepIndexHandler) mContext;
    }

    binding.bNextStep.setOnClickListener(view -> {
      bindStep(mStepIndex + 1);
      binding.svStepDetail.scrollTo(0, 0);
    });

    binding.bNextStep.setVisibility(View.GONE);

    mPlayerView = binding.pvExoPlayer;
    makeSureExoPlayerInitialized();
    mPlayerView.setPlayer(mExoPlayer);
    binding.flExoPlayer.setVisibility(View.GONE);

    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey(NOW_PLAYING_STATE)) {
        mNowPlaying = savedInstanceState.getString(NOW_PLAYING_STATE);
      }
    }

    mRootView = binding.getRoot();

    return binding.getRoot();
  }

  private void makeSureExoPlayerInitialized() {

    if (mExoPlayer == null) {
      // Create an instance of the ExoPlayer.
      TrackSelector trackSelector = new DefaultTrackSelector();
      LoadControl loadControl = new DefaultLoadControl();
      mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);

      // Set the ExoPlayer.EventListener to this activity.
      mExoPlayer.addListener(this);

      mPlayerView.setPlayer(mExoPlayer);
    }
  }

  @Override public void onStart() {
    super.onStart();

    initializeMediaSession();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();

    boolean isOrientationChange = false;
    if (mContext instanceof Activity && ((Activity) mContext).isChangingConfigurations()) {
      isOrientationChange = true;
    }
    if (isOrientationChange) {

      mExoPlayer.setPlayWhenReady(false);
    } else {
      releasePlayer();
      mMediaSession.setActive(false);
    }
  }

  @Override public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(NOW_PLAYING_STATE, mNowPlaying);
  }

  public void unloadStep() {
    binding.ivButtonPlaceholder.setVisibility(View.GONE);
    binding.bNextStep.setVisibility(View.GONE);
    binding.tvShortDescription.setText("");
    binding.tvStepDescription.setText("");
  }

  public void setHasNextStep(boolean hasNextStep) {
    if (hasNextStep) {
      binding.ivButtonPlaceholder.setVisibility(View.VISIBLE);
      binding.bNextStep.setVisibility(View.VISIBLE);
    } else {
      binding.ivButtonPlaceholder.setVisibility(View.GONE);
      binding.bNextStep.setVisibility(View.GONE);
    }
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public void bindStep(int index) {

    if (index == mStepIndex) {
      return;
    }

    unloadStep();

    if (recipe == null) {
      return;
    }

    if (index >= recipe.getSteps().size()) {
      return;
    }

    Step step = recipe.getSteps().get(index);

    //save index for next button
    mStepIndex = index;
    setHasNextStep(index < recipe.getSteps().size() - 1);

    if (mSetStepIndexHandler != null) {
      mSetStepIndexHandler.handleSetStepIndex(index);
    }

    binding.tvShortDescription.setText(step.getShortDescription());
    binding.tvStepDescription.setText(step.getDescription());

    boolean showPlayer = false;
    try {
      if (step.getVideoURL().length() > 0) {
        binding.ivThumbnail.setVisibility(View.GONE);
        binding.pvExoPlayer.setVisibility(View.VISIBLE);

        Uri videoUri = Uri.parse(step.getVideoURL());
        if (videoUri != null) {
          showPlayer = true;
          bindExoPlayer(videoUri);
        }
      } else {
        binding.ivThumbnail.setVisibility(View.VISIBLE);
        binding.pvExoPlayer.setVisibility(View.GONE);

        Picasso.with(getActivity())
            .load(step.getThumbnailURL())
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.err)
            .into(binding.ivThumbnail);
      }
    } catch (Exception ex) {
      showPlayer = false;
      Log.e("EXO_PLAYER", "Failed to load video: " + ex.getLocalizedMessage());
    }

    if (showPlayer) {
      binding.flExoPlayer.setVisibility(View.VISIBLE);
      delayedResizeExoPlayer();
    } else {
      if (mExoPlayer != null) {
        mExoPlayer.stop();
      }
      binding.flExoPlayer.setVisibility(View.GONE);
    }
  }

  public void delayedResizeExoPlayer() {
    mRootView.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            double fragmentWidth = (float) binding.flExoPlayer.getWidth();
            //set the dimensions to 16:9 regardless of screen size.
            int heightToSet = (int) Math.floor(fragmentWidth / 16.0 * 9.0);
            binding.flExoPlayer.getLayoutParams().height = heightToSet;
            binding.flExoPlayer.requestLayout();
            mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        });
  }

  /**
   * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
   * and media controller.
   */
  private void initializeMediaSession() {

    if (mMediaSession == null) {
      // Create a MediaSessionCompat.
      mMediaSession = new MediaSessionCompat(mContext, "TAG");

      // Enable callbacks from MediaButtons and TransportControls.
      mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
          | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

      // Do not let MediaButtons restart the player when the app is not visible.
      mMediaSession.setMediaButtonReceiver(null);

      // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
      mStateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY
          | PlaybackStateCompat.ACTION_PAUSE
          | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
          | PlaybackStateCompat.ACTION_PLAY_PAUSE);

      mMediaSession.setPlaybackState(mStateBuilder.build());

      // MySessionCallback has methods that handle callbacks from a media controller.
      mMediaSession.setCallback(new MySessionCallback());

      // Start the Media Session since the activity is active.
      mMediaSession.setActive(true);
    }
  }

  /**
   * Release ExoPlayer.
   */
  private void releasePlayer() {
    if (mExoPlayer != null) {
      mExoPlayer.stop();
      mExoPlayer.release();
      mExoPlayer = null;
    }
  }

  @Override public void onTimelineChanged(Timeline timeline, Object manifest) {

  }

  @Override
  public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

  }

  @Override public void onLoadingChanged(boolean isLoading) {

  }

  @Override public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

  }

  @Override public void onPlayerError(ExoPlaybackException error) {

  }

  @Override public void onPositionDiscontinuity() {

  }

  @Override public void onPause() {
    super.onPause();
    if (Util.SDK_INT <= 23) {
      releasePlayer();
    }
  }

  @Override public void onStop() {
    super.onStop();
    if (Util.SDK_INT > 23) {
      releasePlayer();
    }
  }

  public void skipOneExoBind() {
    mSkipOneExoBind = true;
  }

  /**
   * Initialize ExoPlayer.
   *
   * @param mediaUri The URI of the sample to play.
   */
  private void bindExoPlayer(Uri mediaUri) {

    String uriString = mediaUri.toString();
    //do not restart if binding the same thing
    //especially if changing orientation.
    if (mNowPlaying.equals(uriString)) {

      if (mExoPlayer != null) {
        mExoPlayer.setPlayWhenReady(true);
      }
      return;
    }

    makeSureExoPlayerInitialized();

    if (mExoPlayer != null) {
      mExoPlayer.stop();

      // Prepare the MediaSource.
      String userAgent = Util.getUserAgent(mContext, BuildConfig.APPLICATION_ID);
      MediaSource mediaSource =
          new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(mContext, userAgent),
              new DefaultExtractorsFactory(), null, null);
      mExoPlayer.prepare(mediaSource);
      mExoPlayer.setPlayWhenReady(true);

      mNowPlaying = uriString;
    }
  }

  public interface SetStepIndexHandler {
    void handleSetStepIndex(int index);
  }

  /**
   * Media Session Callbacks, where all external clients control the player.
   */
  private class MySessionCallback extends MediaSessionCompat.Callback {
    @Override public void onPlay() {
      mExoPlayer.setPlayWhenReady(true);
    }

    @Override public void onPause() {
      mExoPlayer.setPlayWhenReady(false);
    }

    @Override public void onSkipToPrevious() {
      mExoPlayer.seekTo(0);
    }
  }
}
