package das.mobile.triptracker.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import das.mobile.triptracker.databinding.ActivityAddPostBinding;
import das.mobile.triptracker.model.Post;

public class AddPostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityAddPostBinding binding;
    private Uri imageUri;

    private DatabaseReference firebaseDB;
    private StorageReference firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDB = FirebaseDatabase.getInstance().getReference("post");
        firebaseStorage = FirebaseStorage.getInstance().getReference("post_images");

        binding.ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();
            binding.ivImagePost2.setImageURI(imageUri);
            binding.cvImagePost2.setVisibility(View.VISIBLE);
        }
    }


    private void savePost() {
        String postText = binding.etPost.getText().toString().trim();

        if (imageUri != null) {
            String postId = UUID.randomUUID().toString();


            StorageReference imageRef = firebaseStorage.child(postId + ".jpg");
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {

                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();

                            Post post = new Post(postId, postText, imageUrl);
                            firebaseDB.child(post.getId()).setValue(post);
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle image upload failure
                    });


        } else {
            // No image selected
            Post post = new Post(UUID.randomUUID().toString(), postText, null);
            firebaseDB.child(post.getId()).setValue(post);
        }
    }
}
