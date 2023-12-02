package das.mobile.triptracker.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import das.mobile.triptracker.databinding.ActivityAddPostBinding;
import das.mobile.triptracker.model.Post;

public class AddPostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityAddPostBinding binding;
    private ArrayList<Uri> imageUri;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference postDB;
    private StorageReference firebaseStorage;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        postDB = FirebaseDatabase.getInstance().getReference("posts");
        firebaseStorage = FirebaseStorage.getInstance().getReference("post_images");
        currentUser = firebaseAuth.getCurrentUser();
        imageUri = new ArrayList<>();

        binding.ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri.size() < 2) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } else {
                    Toast.makeText(AddPostActivity.this, "You can only add two image", Toast.LENGTH_SHORT).show();
                }

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
            imageUri.add(data.getData());
            Log.i("IMAGE_URI", "onActivityResult: " + imageUri.toString());
            if (binding.cvImagePost1.getVisibility() == View.GONE) {
                binding.ivImagePost1.setImageURI(imageUri.get(0));
                binding.cvImagePost1.setVisibility(View.VISIBLE);
            } else {
                binding.ivImagePost2.setImageURI(imageUri.get(1));
                binding.cvImagePost2.setVisibility(View.VISIBLE);
            }
        }
    }


    private void savePost() {
        String postText = binding.etPost.getText().toString().trim();
        String postId = UUID.randomUUID().toString();
        String userId = currentUser.getUid();
        String postDate = getCurrentDate();

        if (imageUri != null) {
            List<String> imageURL = new ArrayList<>();

            for (int i = 0; i < imageUri.size(); i++) {
                StorageReference imageRef = firebaseStorage.child(postId + "_image" + i + ".jpg");
                int finalI = i;
                imageRef.putFile(imageUri.get(0))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageURL.add(uri.toString());
                                        if (finalI == imageUri.size() - 1 ){
                                            Post post = new Post(postId, userId, postText, postDate, imageURL);
                                            postDB.child(post.getId()).setValue(post);
                                            Toast.makeText(getApplicationContext(), "Post Upload Success", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddPostActivity.this, "Upload post failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            Log.i("IMAGEURL", imageURL.toString());

        } else {
            Post post = new Post(postId, userId, postText, postDate, null);
            postDB.child(post.getId()).setValue(post);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        return dateFormat.format(new Date());
    }
}
