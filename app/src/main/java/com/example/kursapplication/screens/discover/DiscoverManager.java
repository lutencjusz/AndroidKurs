package com.example.kursapplication.screens.discover;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.kursapplication.ErrorResponse;
import com.example.kursapplication.UserStorage;
import com.example.kursapplication.api.ErrorConverter;
import com.example.kursapplication.api.Podcast;
import com.example.kursapplication.api.PodcastApi;
import com.example.kursapplication.api.PodcastResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverManager {
    private final PodcastApi podcastApi;
    private Call<PodcastResponse> call;
    private Call<Subscription> subscriptionCall;
    private DiscoverFragment discoverFragment;
    private final Bus bus;
    private final UserStorage userStorage;
    private final ErrorConverter errorConverter;
    private final String idDB;
    private final String keyDB;

    public DiscoverManager(PodcastApi podcastApi, Bus bus, UserStorage userStorage, ErrorConverter errorConverter, String idDB, String keyDB) {
        this.podcastApi = podcastApi;
        this.bus = bus;
        this.userStorage = userStorage;
        this.errorConverter = errorConverter;
        this.idDB = idDB;
        this.keyDB = keyDB;
        bus.register(this);
    }

    public void onAttach(DiscoverFragment discoverFragment) {
        this.discoverFragment = discoverFragment;
    }

    public void onStop() {
        this.discoverFragment = null;
    }

    public void loadPodcasts(String idBD, String keyDB) {
        call = podcastApi.getPodcasts(idBD, keyDB);
        call.enqueue(new Callback<PodcastResponse>() {
            @Override
            public void onResponse(@NonNull Call<PodcastResponse> call, @NonNull Response<PodcastResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    for (Podcast podcast : response.body().results) {
                        Log.d(DiscoverManager.class.getSimpleName(), "Podcast: " + podcast);

                    }
                    if (discoverFragment != null) {
                        discoverFragment.showPodcasts(response.body().results);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<PodcastResponse> call, @NonNull Throwable t) {

            }
        });
    }

    @Subscribe
    public void onAddPodcast(AddPodcastEvent event) {
        Log.d(DiscoverManager.class.getSimpleName(), "Dodano:" + event.podcast);
        saveSubscription(event.podcast);
    }

    private void saveSubscription(Podcast podcast) {

        String userId = userStorage.getUserId();

        Subscription subscription = new Subscription();
        subscription.podcastId = podcast.podcastId;
        subscription.userId = userId;
        subscription.acl = new JsonObject();

        JsonObject aclJson = new JsonObject();
        aclJson.add("read", new JsonPrimitive(true));
        aclJson.add("write", new JsonPrimitive(true));

        subscription.acl.add(userId, aclJson);

        subscriptionCall = podcastApi.postSubscription(subscription, userStorage.getToken(), idDB, keyDB);
        subscriptionCall.enqueue(new Callback<Subscription>() {
            @Override
            public void onResponse(@NonNull Call<Subscription> call, @NonNull Response<Subscription> response) {
                if (response.isSuccessful()) {
                    if (discoverFragment != null) {
                        discoverFragment.saveSuccessful();
                    }
                } else {
                    ErrorResponse errorResponse = errorConverter.convert(response.errorBody());
                    if (discoverFragment != null && errorResponse != null) {
                        discoverFragment.showError(errorResponse.error);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Subscription> call, @NonNull Throwable t) {
                if (discoverFragment != null) {
                    discoverFragment.showError(t.getLocalizedMessage());
                }
            }
        });
    }

}
