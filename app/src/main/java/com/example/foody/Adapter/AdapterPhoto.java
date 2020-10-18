package com.example.foody.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterPhoto extends RecyclerView.Adapter<AdapterPhoto.ViewHolder> {
    Context context;
    ArrayList<Uri> uriArrayList;

    public AdapterPhoto(Context context) {
        this.context = context;

    }

    public void setData(ArrayList<Uri> uriArrayList) {
        this.uriArrayList = uriArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_hinhanh_duocchon, parent, false);

        return new AdapterPhoto.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Uri uri = uriArrayList.get(position);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            holder.img.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (uriArrayList == null) {
            return 0;
        } else {
            return uriArrayList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_photo);
        }
    }
}
