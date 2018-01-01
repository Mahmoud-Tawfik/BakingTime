package com.mahmoud.android.bakingtime.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahmoud.android.bakingtime.R;
import com.mahmoud.android.bakingtime.model.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mahmoud on 9/28/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {
    private List<Step> mStep;

    public StepsAdapter() {
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        int layoutIdForListItem = R.layout.step_list_item;
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new StepsAdapterViewHolder(view);
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mThumnailImageView;
        public final TextView mShortDescriptionTextView;
        public final TextView mDescriptionTextView;
        public final ImageView mVideoImageView;

        public StepsAdapterViewHolder(View view) {
            super(view);

            mThumnailImageView = (ImageView) view.findViewById(R.id.thumbnail_image_view);
            mShortDescriptionTextView = (TextView) view.findViewById(R.id.short_decription_text_view);
            mDescriptionTextView = (TextView) view.findViewById(R.id.decription_text_view);
            mVideoImageView = (ImageView) view.findViewById(R.id.video_image_view);
        }
    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {
        Step step = mStep.get(position);

        holder.mShortDescriptionTextView.setText(step.getShortDescription());
        holder.mDescriptionTextView.setText(step.getDescription());

        Context context = holder.mThumnailImageView.getContext();
        Uri uri = Uri.parse(step.getThumbnailURL());
        Picasso.with(context).load(uri).into(holder.mThumnailImageView);

        holder.mVideoImageView.setVisibility(step.getVideoURL().isEmpty() ? View.INVISIBLE : View.VISIBLE);
        holder.mVideoImageView.setId(position);

        ImageView.OnClickListener videoClickListener = new ImageView.OnClickListener() {
            public void onClick(View v) {
                String videoUrl = mStep.get(v.getId()).getVideoURL();

//                DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
//                        Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter);
//                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//                MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri,
//                        dataSourceFactory, extractorsFactory, null, null);
//                player.prepare(videoSource);

            }
        };
        holder.mVideoImageView.setOnClickListener(videoClickListener);

    }

    @Override
    public int getItemCount() {
        if (null == mStep) return 0;
        return mStep.size();
    }

    public void setSteps(List<Step> steps){
        mStep = steps;
        notifyDataSetChanged();
    }

}