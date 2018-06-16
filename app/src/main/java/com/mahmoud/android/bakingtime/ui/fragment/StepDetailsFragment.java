package com.mahmoud.android.bakingtime.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Recipe;
import com.mahmoud.android.bakingtime.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mahmoud on 1/1/18.
 */

public class StepDetailsFragment extends Fragment {
    private final static String CURRENT_PLAY_STATE = "current_play_state";
    private final static String CURRENT_PLAYER_POSITION = "current_player_position";
    private final static String CURRENT_RECIPE = "current_recipe";
    private final static String CURRENT_STEP = "current_step";
    Boolean currentPlayState = true;
    long currentPlayerPosition = 0L;

    public Recipe recipe;
    public int currentStep;
    SimpleExoPlayer player;

    public @BindView(R.id.exoplayer) PlayerView playerView;
    public @Nullable @BindView(R.id.step_short_description_text_view) TextView mShortDescriptionTextView;
    public @Nullable @BindView(R.id.step_description_text_view) TextView mDescriptionTextView;

    public StepDetailsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null) {
            currentPlayState = savedInstanceState.getBoolean(CURRENT_PLAY_STATE);
            currentPlayerPosition = savedInstanceState.getLong(CURRENT_PLAYER_POSITION);
            recipe = savedInstanceState.getParcelable(CURRENT_RECIPE);
            currentStep = savedInstanceState.getInt(CURRENT_STEP);
        }

        if (recipe != null){
            updateUI();
        }

        return rootView;
    }

    public void updateUI(){
        Step step = recipe.getSteps().get(currentStep);
        if (mShortDescriptionTextView != null) mShortDescriptionTextView.setText(step.getShortDescription());
        if (mDescriptionTextView != null) mDescriptionTextView.setText(step.getDescription());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recipe != null && recipe.getSteps().get(currentStep).getVideoURL().isEmpty()){
            playerView.setVisibility(View.INVISIBLE);
        } else {
            playerView.setVisibility(View.VISIBLE);
            if (player == null){
                initializePlayer();
            } else {
                playerView.switchTargetView(player, playerView, playerView);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            currentPlayState = player.getPlayWhenReady();
            currentPlayerPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(CURRENT_PLAY_STATE, player != null ? player.getPlayWhenReady() : currentPlayState);
        outState.putLong(CURRENT_PLAYER_POSITION, player != null ? player.getCurrentPosition() : currentPlayerPosition);
        outState.putParcelable(CURRENT_RECIPE, recipe);
        outState.putInt(CURRENT_STEP, currentStep);
        super.onSaveInstanceState(outState);
    }

    private void initializePlayer(){
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "Exoplayer"));

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        Uri videoUri = Uri.parse(recipe.getSteps().get(currentStep).getVideoURL());
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);

        player.prepare(videoSource);
        player.setPlayWhenReady(currentPlayState);
        player.seekTo(currentPlayerPosition);
    }
}