package das.mobile.triptracker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import das.mobile.triptracker.databinding.ItemPostBinding;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    int size;

    public PostAdapter(int size) {
        this.size = size;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.size;
    }

    class PostViewHolder extends RecyclerView.ViewHolder{

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
        }
    }
}
