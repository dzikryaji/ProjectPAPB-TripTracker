package das.mobile.triptracker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import das.mobile.triptracker.activity.LoginActivity;
import das.mobile.triptracker.adapter.ProfilePagerAdapter;
import das.mobile.triptracker.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    FirebaseUser currentUser;
    public ProfileFragment(FirebaseUser currentUser) {
        this.currentUser = currentUser;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);
        ProfilePagerAdapter pagerAdapter = new ProfilePagerAdapter(requireActivity(), currentUser);
        binding.viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            // Customize tab names if needed
            switch (position) {
                case 0:
                    tab.setText("My Post");
                    break;
                case 1:
                    tab.setText("Saved");
                    break;
            }
        }).attach();

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(), "Logout Success", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        return binding.getRoot();
    }
}