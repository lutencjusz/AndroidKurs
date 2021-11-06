package com.example.kursapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.kursapplication.screens.discover.DiscoverFragment;
import com.example.kursapplication.screens.login.LoginActivity;
import com.example.kursapplication.screens.subscribed.AddActionEvent;
import com.example.kursapplication.screens.subscribed.SubscribedFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kursapplication.databinding.ActivityMainBinding;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SubscribedFragment.Callback {

    private AppBarConfiguration mAppBarConfiguration;
    private UserStorage userStorage;
    private NavigationView navigationView;
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userStorage = ((App) getApplication()).getUserStorage();

        if (userStorage.hasToLogin()) {
            goToLogin();
            return; // dalsza część się nie wywoła
        }

        com.example.kursapplication.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.container);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView drawerNameTextView = headerView.findViewById(R.id.drawerNameTextView);
        TextView drawerEmailTextView = headerView.findViewById(R.id.drawerEmailTextView);

        drawerNameTextView.setText(userStorage.getFullName());
        drawerEmailTextView.setText(userStorage.getEmail());

        bus = ((App)getApplication()).getBus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            userStorage.logout();
            goToLogin();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_discover) {
            showFragment(new DiscoverFragment());
        } else if (id == R.id.nav_subscribe) {
            showFragment(new SubscribedFragment());
        } else {

        }

        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer1.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe
    public void onAddAction (AddActionEvent event) {
    goToDiscover();
    }

    @Override
    public void goToDiscover() {
        MenuItem item = navigationView.getMenu().findItem(R.id.nav_discover);
        item.setChecked(true);
        onNavigationItemSelected(item);
    }
}