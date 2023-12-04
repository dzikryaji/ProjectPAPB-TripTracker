package das.mobile.triptracker.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

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
import das.mobile.triptracker.databinding.FragmentSearchBinding;
import das.mobile.triptracker.model.Post;

public class SearchFragment extends Fragment {
    FirebaseUser currentUser;
    PostAdapter postAdapter;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    ArrayList<Post> listPost;
    FragmentSearchBinding binding;

    public SearchFragment(FirebaseUser currentUser) {
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
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        binding.rvSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        db.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPost = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Post post = item.getValue(Post.class);
                    post.setId(item.getKey());
                    listPost.add(post);
                }
                postAdapter = new PostAdapter(listPost, getActivity(), currentUser);
                binding.rvSearch.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = binding.etSearch.getText().toString();
                    performSearch(query);
                    return true;
                }
                return false;
            }
        });

        return binding.getRoot();
    }

    private void performSearch(String searchQuery) {
        db.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPost = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Post post = item.getValue(Post.class);
                    post.setId(item.getKey());
                    String text = post.getText();
                    if (text.contains(searchQuery)){
                        listPost.add(post);
                    }
                }
                postAdapter = new PostAdapter(listPost, getActivity(), currentUser);
                binding.rvSearch.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}