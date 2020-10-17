package com.example.foody.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Model.BinhLuanModel;
import com.example.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerBinhLuan extends RecyclerView.Adapter<AdapterRecyclerBinhLuan.ViewHolder> {
    final long ONE_MEGABYTE = 1024 * 1024;
    Context context;
    List<BinhLuanModel> binhLuanModelList;
    FirebaseStorage firebaseStorage;

    public AdapterRecyclerBinhLuan(Context context, List<BinhLuanModel> binhLuanModelList) {
        this.context = context;
        this.binhLuanModelList = binhLuanModelList;
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public AdapterRecyclerBinhLuan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_recycler_binhluan, parent, false);

        return new AdapterRecyclerBinhLuan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerBinhLuan.ViewHolder holder, int position) {
        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        setHinhAnhBinhLuan(holder.circleImageView, binhLuanModel.getThanhVienModel().getHinhanh());
        holder.txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
        holder.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        holder.txtDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");


        //set hinh binh luan
        final List<Bitmap> bitmapList = new ArrayList<>();
        for (String linkhinh : binhLuanModel.getListHinhBinhLuan()) {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhquanan").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == binhLuanModel.getListHinhBinhLuan().size()) {
                        AdapterRecyclerViewHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerViewHinhBinhLuan(context, bitmapList, R.layout.custom_layout_hinhbinhluan, binhLuanModel, false);

                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);

                        holder.recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return binhLuanModelList.size() > 5 ? 5 : binhLuanModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txtTieuDeBinhLuan, txtNoiDungBinhLuan, txtDiemBinhLuan;
        RecyclerView recyclerViewHinhBinhLuan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.cicleImageUser1);
            txtTieuDeBinhLuan = itemView.findViewById(R.id.txtTieuDeBinhLuan1);
            txtNoiDungBinhLuan = itemView.findViewById(R.id.txtNoiDungBinhLuan1);
            txtDiemBinhLuan = itemView.findViewById(R.id.txtDiemBinhLuan1);
            recyclerViewHinhBinhLuan = itemView.findViewById(R.id.recyclerViewHinhBinhLuan);
        }
    }


    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh) {
        StorageReference storageHinhanh1 = firebaseStorage.getReference().child("thanhvien").child(linkhinh);
        storageHinhanh1.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
