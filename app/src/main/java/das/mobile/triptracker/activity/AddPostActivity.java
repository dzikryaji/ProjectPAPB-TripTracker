package das.mobile.triptracker.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import das.mobile.triptracker.databinding.ActivityAddPostBinding;

public class AddPostActivity extends AppCompatActivity {

    ActivityAddPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.etPost.setFocusable(true);
        binding.etPost.setSelected(true);
    }
}