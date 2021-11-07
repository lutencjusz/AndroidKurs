package com.example.kursapplication.screens.discover;

import android.util.Log;
import com.example.kursapplication.api.Podcast;
import com.example.kursapplication.api.PodcastApi;
import com.example.kursapplication.api.PodcastResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverManager {
    private final PodcastApi podcastApi;
    private Call<PodcastResponse> call;
    private DiscoverFragment discoverFragment;

    public DiscoverManager(PodcastApi podcastApi) {
        this.podcastApi = podcastApi;
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

}
