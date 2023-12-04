package das.mobile.triptracker.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import das.mobile.triptracker.databinding.ActivityUpdatePostBinding;
import das.mobile.triptracker.model.Post;

public class UpdatePostActivity extends AppCompatActivity {

    ActivityUpdatePostBinding binding;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.postId = getIntent().getStringExtra("postId");

        if (postId == null) {
            finish();
        }

        db.child("posts").orderByChild("id").equalTo(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        Post post = userSnapshot.getValue(Post.class);
                        if (post != null) {
                            binding.etPost.setText(post.getText());
                            if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
                                String imageUrl = post.getImageUrl().get(0); // Assuming the image URL is stored in the first index of the list
                                Glide.with(binding.ivImg1.getContext())
                                        .load(imageUrl)
                                        .into(binding.ivImg1);
                                binding.cvImg1.setVisibility(View.VISIBLE);
                                if (post.getImageUrl().size() > 1) {
                                    String imageUrl2 = post.getImageUrl().get(1); // Assuming the second image URL is in the second index of the list
                                    Glide.with(binding.ivImg1.getContext())
                                            .load(imageUrl2)
                                            .into(binding.ivImg2);
                                    binding.cvImg2.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePost();
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePost();
            }
        });
    }

    private void deletePost() {
        if (postId != null) {
            db.child("posts").child(postId).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Deletion successful
                            Toast.makeText(getApplicationContext(), "Post has been deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle deletion failure
                            Toast.makeText(UpdatePostActivity.this, "Post deletion failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void updatePost() {
        String updatedText = binding.etPost.getText().toString();

        // Update the post text
        db.child("posts").child(postId).child("text").setValue(updatedText)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Update successful
                        Toast.makeText(getApplicationContext(), "Post has been updated", Toast.LENGTH_SHORT).show();
                        finish(); // Finish the activity after update
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle update failure
                        Toast.makeText(UpdatePostActivity.this, "Post update failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}