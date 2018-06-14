package com.mahmoud.android.bakingtime.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
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
            player.release();
            player = null;
        }
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
        player.setPlayWhenReady(true);
    }
}