package das.mobile.triptracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import das.mobile.triptracker.databinding.ItemPostBinding;
import das.mobile.triptracker.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> lPost;
    private Activity activity;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    boolean isSaved = false;
    boolean isUserPost = false;


    public PostAdapter(List<Post>lPost, Activity activity, boolean isSaved, boolean isUserPost) {
        this.lPost = lPost;
        this.activity = activity;
        this.isSaved = isSaved;
        this.isUserPost = isUserPost;
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
            if (data.getImageUrl().size() > 1) {
                String imageUrl2 = data.getImageUrl().get(1); // Assuming the second image URL is in the second index of the list
                Glide.with(holder.binding.ivImg1.getContext())
                        .load(imageUrl2)
                        .into(holder.binding.ivImg2);
            } else {
                // Hide tvImg2 if there's no additional image URL
                holder.binding.ivImg2.setVisibility(View.GONE);
            }
        } else {
            // Hide the image view if there's no image URL
            holder.binding.ivImg1.setVisibility(View.GONE);
        }

        holder.binding.tvUsername.setText("@" + data.getUserId());

        if (isSaved) {
            holder.binding.btnPost.setText("Saved");
        } else if (isUserPost) {
            holder.binding.btnPost.setText("Edit");
        }
    }

    @Override
    public int getItemCount() {
        return lPost.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{

        ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
