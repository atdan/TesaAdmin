package com.example.root.tesaadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.root.tesaadmin.common.Common;
import com.example.root.tesaadmin.model.Faculty;
import com.example.root.tesaadmin.model.Lecturer;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class UploadExecutivesActivity extends AppCompatActivity {

    private static final String TAG = "UploadLecturerActivity";
    private static final int PICK_IMAGE_REQUEST = 71;
    ImageView lecturerImage;

    Button selectImageBtn, uploadBtn;

    EditText name, email, execPost, phoneNumber,  level;

    MaterialSpinner spinner;

    Uri saveUri = null;

    ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference lecturers, rawref;
    FirebaseStorage storage;
    StorageReference storageReference;

    ProgressDialog mDialog;

    String[] categoriesArray = {Common.AGE, Common.CHE,
            Common.CVE, Common.CSC,
            Common.FST, Common.EEE, Common.MEE,
            Common.MSC};

    String formattedDate = "";
    String departmentS;

    String mobileN = "", emailS = "", postS = "", nameS = "", degreeS = "", levelS = "", categoryS;

    Uri filePath = null;
    UploadTask uploadTask;
    Uri downloadUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_executives);


        initViews();

        initFirebase();
        init();
    }

    private void initFirebase() {
        //init Firebase
        database = FirebaseDatabase.getInstance();
        lecturers = database.getReference(Common.NODE_FACULTY_EXECS);
        rawref = database.getReference(Common.NODE_FACULTY_EXECS).child(Common.NODE_RAW_POST);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void initViews() {

        lecturerImage = findViewById(R.id.lecturer_image);

        selectImageBtn = findViewById(R.id.add_image);

        uploadBtn = findViewById(R.id.submit);

        name = findViewById(R.id.name);

        email = findViewById(R.id.email);

        execPost = findViewById(R.id.post);

        phoneNumber = findViewById(R.id.phone_number);


        level = findViewById(R.id.level);

        spinner = findViewById(R.id.departmentSpinner);

        spinner.setItems(categoriesArray);

    }


    private void init() {


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    emailS = email.getText().toString();

                    postS = execPost.getText().toString();
                    mobileN = phoneNumber.getText().toString();
                    categoryS = categoriesArray[spinner.getSelectedIndex()];
                    levelS = level.getText().toString();
                    nameS = name.getText().toString();

                    Log.d(TAG, "onClick: submit button clicked");
                    uploadImage();

                }

            }
        });


        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();

            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setMessage("please wait");
            progressDialog.show();

            String imageName = UUID.randomUUID().toString(), postNumber = UUID.randomUUID().toString();

            final StorageReference ref = storageReference.child("images/" + "lecturers/" + imageName+postNumber);

            departmentS =  categoriesArray[spinner.getSelectedIndex()];

            uploadTask = ref.putFile(filePath);


            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return ref.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUri = task.getResult();



                                Date c = Calendar.getInstance().getTime();


                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                                formattedDate = df.format(c);

                                lecturers = database.getReference(Common.NODE_LECTURERS)
                                        .child(categoriesArray[spinner.getSelectedIndex()]);

                                rawref = database.getReference(Common.NODE_LECTURERS)
                                        .child(Common.NODE_RAW_POST);

                                String key = lecturers.push().getKey();

                                String key2 = rawref.push().getKey();

                                Faculty faculty = new Faculty(nameS,emailS, mobileN, levelS, departmentS, postS, downloadUri.toString());

                                Faculty faculty2 = new Faculty(nameS,emailS, mobileN, levelS, departmentS, postS, downloadUri.toString());



//                                Lecturer post = new Lecturer(nameS, emailS, postS, mobileN, degreeS, levelS,
//                                        departmentS, downloadUri.toString());
//
//                                Lecturer post2 = new Lecturer(nameS, emailS, postS, mobileN, degreeS, levelS,
//                                        departmentS, downloadUri.toString());

                                Map<String, Object> postValues = faculty.toMap();

                                Map<String, Object> postValues2 = faculty2.toMap();



                                Map<String, Object> childValues = new HashMap<>();

                                Map<String, Object> childValues2 = new HashMap<>();


                                assert key != null;
                                childValues.put(key, postValues);

                                assert key2 != null;
                                childValues2.put(key2, postValues2);


                                lecturers.updateChildren(childValues);

                                rawref.updateChildren(childValues2);


                                resetFields();
                                startActivity(new Intent(UploadExecutivesActivity.this, HomeActivity.class));
                                finish();

                                progressDialog.dismiss();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(UploadExecutivesActivity.this,
                                    "Sorry something went wrong, please try again", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void resetFields() {

        name.setText("");
        email.setText("");
        phoneNumber.setText("");
        execPost.setText("");
        level.setText("");
    }

    public boolean validate() {
        boolean valid = true;

        mobileN = phoneNumber.getText().toString();
        emailS = email.getText().toString();
        postS = execPost.getText().toString();
        nameS = name.getText().toString();
        levelS = level.getText().toString();


        if (mobileN.isEmpty() || mobileN.length() != 11) {
            phoneNumber.setError("Please enter valid number");
            valid = false;
        } else {
            phoneNumber.setError(null);
        }


        if (emailS.isEmpty() ) {

            email.setError("Please enter thee lecturer email");
            valid = false;
        } else {
            email.setError(null);

        }

        if (postS.isEmpty() || postS.length() < 10) {

            execPost.setError("Please enter the office address");
            valid = false;
        } else {
            execPost.setError(null);

        }

        if (nameS.isEmpty()) {

            name.setError("Please enter the name");
            valid = false;
        } else {
            name.setError(null);

        }
        if (levelS.isEmpty()){
            level.setError("Please enter level");
            valid = false;
        }else {
            level.setError(null);
        }


        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  PICK_IMAGE_REQUEST && resultCode == RESULT_OK  && data != null && data.getData() != null){
            filePath = data.getData();

            saveUri = filePath;

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                lecturerImage.setImageBitmap(bitmap);

            }catch (Exception e){

                Log.e(TAG, "onActivityResult: Error picking image" );
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
