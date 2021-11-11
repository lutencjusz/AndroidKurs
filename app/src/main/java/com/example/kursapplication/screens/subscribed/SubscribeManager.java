package com.example.kursapplication.screens.subscribed;

import com.example.kursapplication.UserStorage;
import com.example.kursapplication.api.PodcastApi;
import com.example.kursapplication.api.PodcastResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribeManager {

    private final PodcastApi podcastApi;
    private final UserStorage userStorage;
    private Call<PodcastResponse> call;
    private SubscribedFragment subscribedFragment;

    public SubscribeManager(PodcastApi podcastApi, UserStorage userStorage) {
        this.podcastApi = podcastApi;
        this.userStorage = userStorage;
    }

    public void onAttach(SubscribedFragment subscribedFragment){
        this.subscribedFragment = subscribedFragment;

    }

    public void onStop(){
        this.subscribedFragment = null;
    }


    public void loadPodcast(){
        String where = "{\"podcastId\": {\"$select\": {\"query\": {\"className\": \"Subscription\",\"where\": {\"userId\": \"%s\"},\"key\": \"podcastId\"}}}}";
        call = podcastApi.getSubscribePodcast(String.format(where, userStorage.getUserId()), userStorage.getToken());
        call. enqueue(new Callback<PodcastResponse>() {
            @Override
            public void onResponse(Call<PodcastResponse> call, Response<PodcastResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    if (subscribedFragment != null){
                        subscribedFragment.showPodcasts(response.body().results);
                    }
                }

            }

            @Override
            public void onFailure(Call<PodcastResponse> call, Throwable t) {

            }
        });
    }
}
