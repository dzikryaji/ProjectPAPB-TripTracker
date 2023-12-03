package das.mobile.triptracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import das.mobile.triptracker.adapter.PostAdapter;
import das.mobile.triptracker.databinding.FragmentMyPostBinding;
import das.mobile.triptracker.model.Post;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    PostAdapter postAdapter;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    ArrayList<Post> listPost;

    public MyPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPostFragment newInstance(String param1, String param2) {
        MyPostFragment fragment = new MyPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMyPostBinding binding = FragmentMyPostBinding.inflate(inflater, container, false);
        binding.rvMyPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        db.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPost = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Post post = item.getValue(Post.class);
                    post.setId(item.getKey());
                    listPost.add(post);
                }
                postAdapter = new PostAdapter(listPost, getActivity(), false, true);
                binding.rvMyPost.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}