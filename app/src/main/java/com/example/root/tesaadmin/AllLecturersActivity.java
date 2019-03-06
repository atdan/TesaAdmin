package com.example.root.tesaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.root.tesaadmin.common.Common;
import com.example.root.tesaadmin.interfaces.ItemClickListener;
import com.example.root.tesaadmin.model.Lecturer;
import com.example.root.tesaadmin.viewHolder.HomeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AllLecturersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private static final String TAG = "HomeActivity";
    FirebaseDatabase database;
    DatabaseReference leturers;

    FirebaseStorage storage;
    StorageReference storageReference;


    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Lecturer, HomeViewHolder> adapter;


    FirebaseAuth auth;

    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lecturers);

        init();



        loadList();

    }

    private void init() {
        recyclerView = findViewById(R.id.all_lecturers_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        leturers = database.getReference(Common.NODE_LECTURERS).child(Common.NODE_RAW_POST);


    }

    private void loadList() {

        Query query = leturers.orderByChild("department");

        FirebaseRecyclerOptions<Lecturer> options = new FirebaseRecyclerOptions.Builder<Lecturer>()
                .setQuery(query,Lecturer.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Lecturer, HomeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HomeViewHolder holder, int position, @NonNull Lecturer model) {


                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.image_processing);
                requestOptions.error(R.drawable.no_image);


                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(requestOptions)
                        .setDefaultRequestOptions(requestOptions)
                        .load(model.getImageUrl())
                        .into(holder.eachImage);

                Log.e(TAG, "onBindViewHolder: " + model.getImageUrl() );

                holder.eachName.setText(model.getName());
                holder.eachDept.setText(model.getDepartment());
                holder.eachNumber.setText(model.getPhoneNumber());

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent intent = new Intent(AllLecturersActivity.this, LecturerDetailActivity.class);
                        intent.putExtra("postid", adapter.getRef(position).getKey());
                        intent.putExtra(Common.FROM_ACTIVITY,Common.ALL_LECTURERS_ACTIVITY);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public HomeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.each_lecturer, viewGroup, false);

                return new HomeViewHolder(itemView);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
}
