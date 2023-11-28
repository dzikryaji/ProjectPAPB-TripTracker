package das.mobile.triptracker.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

import das.mobile.triptracker.R;
import das.mobile.triptracker.databinding.ActivityMainBinding;
import das.mobile.triptracker.fragment.HomeFragment;
import das.mobile.triptracker.fragment.NewsFragment;
import das.mobile.triptracker.fragment.ProfileFragment;
import das.mobile.triptracker.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // make status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        HomeFragment homeFragment = new HomeFragment();
        SearchFragment searchFragment = new SearchFragment();
        NewsFragment newsFragment = new NewsFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        setCurrentFragment(homeFragment);

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    setCurrentFragment(homeFragment);
                } else if (item.getItemId() == R.id.nav_search) {
                    setCurrentFragment(searchFragment);
                } else if (item.getItemId() == R.id.nav_news) {
                    setCurrentFragment(newsFragment);
                } else if (item.getItemId() == R.id.nav_profile) {
                    setCurrentFragment(profileFragment);
                }
                return true;
            }
        });
    }

    private void setCurrentFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, fragment).commit();
    }
}