package com.example.kursapplication.screens.discover;

import com.example.kursapplication.api.Podcast;

public class AddPodcastEvent {
    public final Podcast podcast;

    public AddPodcastEvent(Podcast podcast) {
        this.podcast = podcast;

    }
}
