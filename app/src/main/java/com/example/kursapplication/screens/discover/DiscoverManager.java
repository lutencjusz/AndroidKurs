package com.example.kursapplication.screens.discover;

import android.util.Log;
import com.example.kursapplication.api.Podcast;
import com.example.kursapplication.api.PodcastApi;
import com.example.kursapplication.api.PodcastResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverManager {
    private final PodcastApi podcastApi;
    private Call<PodcastResponse> call;
    private DiscoverFragment discoverFragment;
    private final Bus bus;

    public DiscoverManager(PodcastApi podcastApi, Bus bus) {
        this.podcastApi = podcastApi;
        this.bus = bus;
        bus.register(this);
    }

    public void onAttach(DiscoverFragment discoverFragment){
        this.discoverFragment = discoverFragment;
    }

    public void onStop(){
        this.discoverFragment = null;
    }

    public void loadPodcasts(String idBD, String keyDB) {
        call = podcastApi.getPodcasts(idBD, keyDB);
        call.enqueue(new Callback<PodcastResponse>() {
            @Override
            public void onResponse(Call<PodcastResponse> call, Response<PodcastResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    for (Podcast podcast : response.body().results) {
                        Log.d(DiscoverManager.class.getSimpleName(), "Podcast: " + podcast);

                    }
                    if(discoverFragment != null){
                        discoverFragment.showPodcasts(response.body().results);
                    }

                }
            }

            @Override
            public void onFailure(Call<PodcastResponse> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void onAddPodcast(AddPodcastEvent event){
        Log.d(DiscoverManager.class.getSimpleName() ,"Dodano:" + event.podcast);
        saveSubscription(event.podcast);
    }

    private void saveSubscription(Podcast podcast) {
        Subscription subscription = new Subscription();
        subscription.podcastId = podcast.podcastId;
        subscription.userId =

    }

}
