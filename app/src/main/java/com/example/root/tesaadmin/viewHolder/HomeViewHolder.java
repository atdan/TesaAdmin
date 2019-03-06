package com.example.root.tesaadmin.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.tesaadmin.R;
import com.example.root.tesaadmin.interfaces.ItemClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public ImageView eachImage;

    public TextView eachName, eachDept, eachNumber;

    private ItemClickListener itemClickListener;


    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);

        eachImage = itemView.findViewById(R.id.each_image);
        eachDept = itemView.findViewById(R.id.each_dept);
        eachName = itemView.findViewById(R.id.each_name);
        eachNumber = itemView.findViewById(R.id.each_number);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
