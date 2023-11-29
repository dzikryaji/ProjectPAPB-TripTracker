package das.mobile.triptracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import das.mobile.triptracker.databinding.ActivityRegisterBinding;
import das.mobile.triptracker.model.User;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private DatabaseReference firebaseDB;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-papb-7de12-default-rtdb.asia-southeast1.firebasedatabase.app/");

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = binding.etFirstName.getText().toString().trim();
                String lastName = binding.etLastName.getText().toString().trim();
                String phone = binding.etPhone.getText().toString().trim();
                String age = binding.etAge.getText().toString().trim();
                String username = binding.etUsername.getText().toString().trim();
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                User user = new User(username, email, firstName, lastName, age, phone);

                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || age.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Seluruh data harus terisi", Toast.LENGTH_SHORT).show();
                } else if (binding.cbAccept.isChecked()) {
                    firebaseDB = FirebaseDatabase.getInstance().getReference("users");
                    firebaseDB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(username).exists()) {
                                Toast.makeText(RegisterActivity.this, "Username sudah diambil", Toast.LENGTH_SHORT).show();
                            } else {
                                firebaseDB.child(username).setValue(user);
                                firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Register berhasil", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), OnBoarding4Activity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Log.e("AUTH", "createUserWithEmail:failure", task.getException());
                                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("DATABASE", "createUserWithEmail:failure " + error.getMessage());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Ceklis syarat dan ketentuan", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}