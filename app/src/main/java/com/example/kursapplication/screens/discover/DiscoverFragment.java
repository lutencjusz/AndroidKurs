package com.example.kursapplication.screens.discover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kursapplication.App;
import com.example.kursapplication.R;
import com.example.kursapplication.api.Podcast;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        discoverManager = ((App)getActivity().getApplication()).getDiscoverManager();
        idDB=((App)getActivity().getApplication()).getIdDB();
        keyDB=((App)getActivity().getApplication()).getKeyDB();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
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
DiscoverAdapter
    }
}
