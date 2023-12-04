package das.mobile.triptracker.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.firebase.auth.FirebaseUser;

import das.mobile.triptracker.fragment.MyPostFragment;
import das.mobile.triptracker.fragment.SavedFragment;

public class ProfilePagerAdapter extends FragmentStateAdapter {
    FirebaseUser currentUser;
    public ProfilePagerAdapter(@NonNull FragmentActivity fragmentActivity, FirebaseUser currentUser) {
        super(fragmentActivity);
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyPostFragment(currentUser);
            case 1:
                return new SavedFragment(currentUser);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
