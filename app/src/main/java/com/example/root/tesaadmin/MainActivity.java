package com.example.root.tesaadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPassword;
    Button register;

    TextView signInTxt;

    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.email);

        edtName = findViewById(R.id.name);

        edtPassword = findViewById(R.id.password);

        register = findViewById(R.id.registerBtn);

        signInTxt = findViewById(R.id.signInTxt);

        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUsername = edtName.getText().toString();
                String txtemail = edtEmail.getText().toString();
                String txtpassword = edtPassword.getText().toString();

                if (TextUtils.isEmpty(txtUsername) ||TextUtils.isEmpty(txtemail) ||
                        TextUtils.isEmpty(txtpassword)){
                    Toast.makeText(MainActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }else if (txtpassword.length() < 6){
                    Toast.makeText(MainActivity.this, "password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                }else {
                    register(txtUsername,txtemail,txtpassword);
                }
            }
        });


    }

    private void register(final String username, String email, final String pasword){

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Creating user");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,pasword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            databaseReference.keepSynced(true);

                            HashMap<String ,String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("name",username);
                            hashMap.put("imageUrl",null);
                            hashMap.put("status","offline");
                            hashMap.put("password",pasword);



                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                    }else {
                                        Toast.makeText(MainActivity.this, "You can't register with this email", Toast.LENGTH_SHORT).show();

                                        progressDialog.dismiss();
                                    }
                                }
                            });

                        }
                    }
                });
    }
}
