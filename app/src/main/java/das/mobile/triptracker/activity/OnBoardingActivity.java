package das.mobile.triptracker.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import das.mobile.triptracker.adapter.OnBoardingPagerAdapter;
import das.mobile.triptracker.databinding.ActivityOnBoardingBinding;
import das.mobile.triptracker.fragment.OnBoarding1Fragment;
import das.mobile.triptracker.fragment.OnBoarding2Fragment;
import das.mobile.triptracker.fragment.OnBoarding3Fragment;

public class OnBoardingActivity extends AppCompatActivity {

    ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // make status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OnBoarding1Fragment());
        fragments.add(new OnBoarding2Fragment());
        fragments.add(new OnBoarding3Fragment());
        OnBoardingPagerAdapter pagerAdapter = new OnBoardingPagerAdapter(this, fragments);
        binding.viewpager.setAdapter(pagerAdapter);
    }
}