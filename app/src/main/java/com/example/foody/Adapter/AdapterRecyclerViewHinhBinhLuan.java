package com.example.foody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Model.BinhLuanModel;
import com.example.foody.R;
import com.example.foody.View.ChiTietBinhLuanActivity;

import java.util.List;

public class AdapterRecyclerViewHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerViewHinhBinhLuan.ViewHolder> {
    Context context;
    List<Bitmap> bitmaps;
    int resource;
    BinhLuanModel binhLuanModel;
    boolean isChiTietBinhLuan;

    public AdapterRecyclerViewHinhBinhLuan(Context context, List<Bitmap> bitmaps, int resource, BinhLuanModel binhLuanModel, boolean isChiTietBinhLuan) {
        this.context = context;
        this.bitmaps = bitmaps;
        this.resource = resource;
        this.binhLuanModel = binhLuanModel;
        this.isChiTietBinhLuan = isChiTietBinhLuan;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new AdapterRecyclerViewHinhBinhLuan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgHinhBinhLuan.setImageBitmap(bitmaps.get(position));
        if (position == 3 && isChiTietBinhLuan == false) {
            int sohinhconlai = bitmaps.size() - 4;
            holder.frameLayoutHinh.setVisibility(View.VISIBLE);
            holder.txtSoHinhBinhLuan.setText("+" + sohinhconlai);
            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iChiTietBinhLuan = new Intent(context, ChiTietBinhLuanActivity.class);
                    iChiTietBinhLuan.putExtra("binhluan", binhLuanModel);
                    context.startActivity(iChiTietBinhLuan);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return isChiTietBinhLuan ? bitmaps.size() : bitmaps.size() > 4 ? 4 : bitmaps.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhBinhLuan;
        TextView txtSoHinhBinhLuan;
        FrameLayout frameLayoutHinh, frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhBinhLuan = itemView.findViewById(R.id.imgHinhBinhLuan);
            txtSoHinhBinhLuan = itemView.findViewById(R.id.txtSoHinhBinhLuan);
            frameLayoutHinh = itemView.findViewById(R.id.frameLayoutHinh);
            frameLayout = itemView.findViewById(R.id.frame);
        }
    }
}
