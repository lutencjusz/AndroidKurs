package com.example.kursapplication.screens.discover;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.kursapplication.R;
import com.example.kursapplication.api.Podcast;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverViewHolder> {

    private final Bus bus;
    private List<Podcast> podcastList = new ArrayList<>();

    public DiscoverAdapter(Bus bus) {

        this.bus = bus;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new DiscoverViewHolder(layoutInflater.inflate(R.layout.item_discover, parent, false), bus);
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

    private final Bus bus;
    private Podcast podcast;

    public DiscoverViewHolder(@NonNull View itemView, Bus bus) {
        super(itemView);
        this.bus = bus;
        ButterKnife.bind(this, itemView);
    }

    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
        tvPodcastName.setText(podcast.title);
        String episodes = tvPodcastEpisodesCount.getResources().getString(R.string.episodes_count, podcast.numberOfEpisodes);
        tvPodcastEpisodesCount.setText(episodes);
        Glide.with(tvPodcastName.getContext())
                .load(podcast.url)
                .placeholder(R.drawable.placeholder)
                .into(ivPodcastCover);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnAdd)
    public void addPodcast(){
        bus.post(new AddPodcastEvent(podcast));
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.ivPodcastCover)
    public void addPodcastIV(){
        bus.post(new AddPodcastEvent(podcast));
    }
}
