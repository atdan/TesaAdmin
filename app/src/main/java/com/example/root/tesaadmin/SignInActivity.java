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
import android.widget.Toast;

import com.example.root.tesaadmin.common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button signIn;

    //firebase
    FirebaseDatabase database;
    DatabaseReference table_user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //init Firebase
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);

        signIn = findViewById(R.id.signInbtn);

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (auth.getCurrentUser() == null || !auth.getCurrentUser().isEmailVerified()){

                }else {

                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInternet(getBaseContext())){

                    // TODO: save user and password

                    final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();


                    String txtemail = edtEmail.getText().toString();
                    String txtpassword = edtPassword.getText().toString();

                    if (TextUtils.isEmpty(txtemail) ||
                            TextUtils.isEmpty(txtpassword)){

                        Toast.makeText(SignInActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

                    }else {
                        auth.signInWithEmailAndPassword(txtemail,txtpassword)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            progressDialog.dismiss();
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(SignInActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();

                                        }
                                    }
                                });
                    }
                }
                else {
                    Toast.makeText(SignInActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
