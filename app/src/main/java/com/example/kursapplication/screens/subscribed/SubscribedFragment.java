package com.example.kursapplication.screens.subscribed;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kursapplication.App;
import com.example.kursapplication.MainActivity;
import com.example.kursapplication.R;
import com.example.kursapplication.api.Podcast;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribedFragment extends Fragment {

    private SubscribeManager subscribeManager;

    @BindView(R.id.subscribedRecycleView)
    RecyclerView subscribedRecycleView;

    public interface Callback {
        void goToDiscover();
    }

    public Callback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        subscribeManager.onAttach(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        subscribeManager.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        subscribeManager = ((App) getActivity().getApplication()).getSubscribeManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.subscribe, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                App app = (App) getActivity().getApplication();
                app.getBus().post(new AddActionEvent());
                return true;
            case R.id.action_sort:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscribedRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        subscribeManager.loadPodcast();

    }

    public void showPodcasts(List<Podcast> results) {
        SubscribedAdapter subscribedAdapter = new SubscribedAdapter();
        subscribedAdapter.setPodcasts(results);
        subscribedRecycleView.setAdapter(subscribedAdapter);
    }
}
