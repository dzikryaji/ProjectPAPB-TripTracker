package das.mobile.triptracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import das.mobile.triptracker.databinding.ItemPostBinding;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    int size;
    boolean isSaved = false;
    boolean isUserPost = false;

    public PostAdapter(int size) {
        this.size = size;
    }

    public PostAdapter(int size, boolean isSaved, boolean isUserPost) {
        this.size = size;
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
        if (isSaved){
            holder.binding.btnPost.setText("Saved");
        } else if (isUserPost) {
            holder.binding.btnPost.setText("Edit");
        }
    }

    @Override
    public int getItemCount() {
        return this.size;
    }

    class PostViewHolder extends RecyclerView.ViewHolder{

        ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
