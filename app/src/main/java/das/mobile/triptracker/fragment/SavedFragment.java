package das.mobile.triptracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import das.mobile.triptracker.adapter.PostAdapter;
import das.mobile.triptracker.databinding.FragmentSavedBinding;
import das.mobile.triptracker.model.Post;

public class SavedFragment extends Fragment {
    FirebaseUser currentUser;
    PostAdapter postAdapter;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    ArrayList<Post> listPost;

    public SavedFragment(FirebaseUser currentUser) {
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
        FragmentSavedBinding binding = FragmentSavedBinding.inflate(inflater, container, false);
        binding.rvSaved.setLayoutManager(new LinearLayoutManager(getActivity()));
        db.child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPost = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Post post = item.getValue(Post.class);
                    post.setId(item.getKey());
                    listPost.add(post);
                }
                postAdapter = new PostAdapter(listPost, getActivity(), currentUser);
                binding.rvSaved.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}