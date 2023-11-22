package das.mobile.triptracker;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

import das.mobile.triptracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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