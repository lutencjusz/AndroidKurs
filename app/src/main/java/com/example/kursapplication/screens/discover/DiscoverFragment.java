package com.example.kursapplication.screens.discover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kursapplication.App;
import com.example.kursapplication.R;
import com.example.kursapplication.api.Podcast;
import com.squareup.otto.Bus;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.discover_recycleView)
    RecyclerView discoverRecycleView;
    private DiscoverManager discoverManager;
    private String idDB;
    private String keyDB;
    private Bus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = (App) getActivity().getApplication();
        idDB = app.getIdDB();
        keyDB = app.getKeyDB();
        bus = app.getBus();
        discoverManager = app.getDiscoverManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        discoverRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onStart() {
        super.onStart();
        discoverManager.onAttach(this);
        discoverManager.loadPodcasts(idDB, keyDB);
    }

    @Override
    public void onStop() {
        super.onStop();
        discoverManager.onStop();
    }

    public void showPodcasts(List<Podcast> results) {
        DiscoverAdapter adapter = new DiscoverAdapter(bus);
        adapter.setPodcasts(results);
        discoverRecycleView.setAdapter(adapter);

    }

    public void saveSuccessful() {
        bus.post(new SwitchToSubscribeEvent());
    }

    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
