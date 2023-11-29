package das.mobile.triptracker.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import das.mobile.triptracker.adapter.OnBoardingPagerAdapter;
import das.mobile.triptracker.databinding.ActivityOnBoardingBinding;
import das.mobile.triptracker.fragment.OnBoarding1Fragment;
import das.mobile.triptracker.fragment.OnBoarding2Fragment;
import das.mobile.triptracker.fragment.OnBoarding3Fragment;

public class OnBoardingActivity extends AppCompatActivity {

    ActivityOnBoardingBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // make status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        firebaseAuth = FirebaseAuth.getInstance();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OnBoarding1Fragment());
        fragments.add(new OnBoarding2Fragment());
        fragments.add(new OnBoarding3Fragment());
        OnBoardingPagerAdapter pagerAdapter = new OnBoardingPagerAdapter(this, fragments);
        binding.viewpager.setAdapter(pagerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}