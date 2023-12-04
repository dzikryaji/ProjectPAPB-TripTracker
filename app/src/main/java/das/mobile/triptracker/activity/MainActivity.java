package das.mobile.triptracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import das.mobile.triptracker.R;
import das.mobile.triptracker.databinding.ActivityMainBinding;
import das.mobile.triptracker.fragment.HomeFragment;
import das.mobile.triptracker.fragment.NewsFragment;
import das.mobile.triptracker.fragment.ProfileFragment;
import das.mobile.triptracker.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();

        HomeFragment homeFragment = new HomeFragment(currentUser);
        SearchFragment searchFragment = new SearchFragment(currentUser);
        NewsFragment newsFragment = new NewsFragment();
        ProfileFragment profileFragment = new ProfileFragment(currentUser);

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

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}