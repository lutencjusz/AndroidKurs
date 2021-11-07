package com.example.kursapplication.screens.discover;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kursapplication.R;
import com.example.kursapplication.api.Podcast;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverViewHolder> {

    private List<Podcast> podcastList = new ArrayList<>();

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new DiscoverViewHolder(layoutInflater.inflate(R.layout.item_discover, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        holder.setPodcast(podcastList.get(position));
    }

    @Override
    public int getItemCount() {
        return podcastList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPodcasts(List<Podcast> results) {
        podcastList.clear();
        podcastList.addAll(results);
        notifyDataSetChanged();
    }
}

class DiscoverViewHolder extends RecyclerView.ViewHolder {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivPodcastCover)
    ImageView ivPodcastCover;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPodcastName)
    TextView tvPodcastName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPodcastEpisodesCount)
    TextView tvPodcastEpisodesCount;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnAdd)
    ImageButton btnAdd;

    public DiscoverViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setPodcast(Podcast podcast) {
        tvPodcastName.setText(podcast.title);
        String episodes = tvPodcastEpisodesCount.getResources().getString(R.string.episodes_count, podcast.numberOfEpisodes);
        tvPodcastEpisodesCount.setText(episodes);
    }
}
