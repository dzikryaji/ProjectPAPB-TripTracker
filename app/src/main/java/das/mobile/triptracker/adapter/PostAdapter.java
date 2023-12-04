package das.mobile.triptracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import das.mobile.triptracker.databinding.ItemPostBinding;
import das.mobile.triptracker.model.Post;
import das.mobile.triptracker.model.User;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> lPost;
    private Activity activity;
    private FirebaseUser currentUser;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public PostAdapter(List<Post> lPost, Activity activity, FirebaseUser currentUser) {
        this.lPost = lPost;
        this.activity = activity;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        final Post data = lPost.get(position);
        holder.binding.tvDate.setText(data.getDate());
        holder.binding.tvPost.setText(data.getText());

        // Check if the post has an image URL
        if (data.getImageUrl() != null && !data.getImageUrl().isEmpty()) {
            String imageUrl = data.getImageUrl().get(0); // Assuming the image URL is stored in the first index of the list
            Glide.with(holder.binding.ivImg1.getContext())
                    .load(imageUrl)
                    .into(holder.binding.ivImg1);
            holder.binding.cvImg1.setVisibility(View.VISIBLE);
            if (data.getImageUrl().size() > 1) {
                String imageUrl2 = data.getImageUrl().get(1); // Assuming the second image URL is in the second index of the list
                Glide.with(holder.binding.ivImg1.getContext())
                        .load(imageUrl2)
                        .into(holder.binding.ivImg2);
                holder.binding.cvImg2.setVisibility(View.VISIBLE);
            }
        }

        db.child("users").orderByChild("id").equalTo(data.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null) {
                            String name = user.getFirstName() + " " + user.getLastName();
                            String username = "@" + user.getUsername();
                            holder.binding.tvName.setText(name);
                            holder.binding.tvUsername.setText(username);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (data.getUserId().equals(currentUser.getUid())){
            holder.binding.btnPost.setText("Edit");
        }

    }

    @Override
    public int getItemCount() {
        return lPost.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
