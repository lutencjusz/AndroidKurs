package com.example.kursapplication.screens.subscribed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.kursapplication.R;
import com.example.kursapplication.api.Podcast;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribedAdapter extends RecyclerView.Adapter<SubscribedViewHolder> {

    private List<Podcast> podcasts = new ArrayList<>();
    @NonNull
    @Override
    public SubscribedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new SubscribedViewHolder(layoutInflater.inflate(R.layout.item_subscribe, parent, false));
    }

    public void setPodcasts(List<Podcast> podcasts) {
        this.podcasts.clear();
        this.podcasts.addAll(podcasts);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribedViewHolder holder, int position) {
holder.setPodcasts(podcasts.get(position));
    }

    @Override
    public int getItemCount() {
        return podcasts.size();
    }
}

class SubscribedViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.subscribedRecycleView)
    ImageView subscribedRecycleView;
    @BindView(R.id.subscribedTextView)
    TextView subscribedTextView;

    public SubscribedViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setPodcasts(Podcast podcast) {
        subscribedTextView.setText(podcast.title);
        Glide.with(subscribedRecycleView.getContext())
                .load(podcast.url )
                .placeholder(R.drawable.placeholder)
                .into(subscribedRecycleView);

    }
}
