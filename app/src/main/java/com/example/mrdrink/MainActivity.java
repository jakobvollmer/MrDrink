package com.example.mrdrink;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import com.example.mrdrink.ui.all.AllFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.mrdrink.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        androidx.appcompat.widget.Toolbar myToolbar = (
                androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_favourites, R.id.navigation_all, R.id.navigation_history)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        setCallbackForSearch();
        setCallbackForFilter();

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
    }

    public void setCallbackForSearch (){
        SearchView searchView = findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment navHostFragment = fm.findFragmentById(R.id.nav_host_fragment_activity_main);
                Fragment currentPage = navHostFragment.getChildFragmentManager().getFragments().get(0);

                if(currentPage instanceof AllFragment) {
                    AllFragment allFragment = (AllFragment) currentPage;
                    allFragment.setSearchString(s);
                }

                return false;
            }
        });
    }

    private void setCallbackForFilter (){
        ImageButton btn = (ImageButton) findViewById(R.id.filter_menu_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment navHostFragment = fm.findFragmentById(R.id.nav_host_fragment_activity_main);
                Fragment currentPage = navHostFragment.getChildFragmentManager().getFragments().get(0);

                if(currentPage instanceof AllFragment) {
                    AllFragment allFragment = (AllFragment) currentPage;
                    allFragment.setFilterView();
                }
            }
        });
    }
}