package com.example.foody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.foody.Model.BinhLuanModel;
import com.example.foody.Model.ChiNhanhQuanAnModel;
import com.example.foody.Model.QuanAnModel;
import com.example.foody.R;
import com.example.foody.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecycleODau extends RecyclerView.Adapter<AdapterRecycleODau.ViewHolder> {
    final long ONE_MEGABYTE = 1024 * 1024;
    FirebaseStorage firebaseStorage;
    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;

    public AdapterRecycleODau(Context context, List<QuanAnModel> quanAnModelList, int resource) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);
        //nút giao hàng
        holder.txtTenQuanAnODau.setText(quanAnModel.getTenquanan());
        if (quanAnModel.isGiaohang()) {
            holder.btnDatMonODau.setVisibility(View.VISIBLE);
        } else {
            holder.btnDatMonODau.setVisibility(View.GONE);
        }
        //end nút giao hàng
        //hình quán ăn
        if (quanAnModel.getBitmapList().size() > 0) {
            holder.imgHinhQuanAn.setImageBitmap(quanAnModel.getBitmapList().get(0));
        }
        //end hình quán ăn


        //bình luận
        if (quanAnModel.getBinhLuanModelList().size() > 0) {
            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTieuDeBinhLuan1.setText(binhLuanModel.getTieude());
            holder.txtNoiDungBinhLuan1.setText(binhLuanModel.getNoidung());
            setHinhAnhBinhLuan(holder.circleImageView1, binhLuanModel.getThanhVienModel().getHinhanh());
            holder.txtDiemBinhLuan1.setText(binhLuanModel.getChamdiem() + "");


            if (quanAnModel.getBinhLuanModelList().size() > 2) {
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModelList().get(2);
                holder.txtTieuDeBinhLuan2.setText(binhLuanModel2.getTieude());
                holder.txtNoiDungBinhLuan2.setText(binhLuanModel2.getNoidung());
                holder.txtDiemBinhLuan2.setText(binhLuanModel2.getChamdiem() + "");
                setHinhAnhBinhLuan(holder.circleImageView2, binhLuanModel2.getThanhVienModel().getHinhanh());
            }


            //tổng bình luận và điểm trung bình
            int tongsohinhbinhluan = 0;
            double tongdiem = 0;
            for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModelList()) {
                tongsohinhbinhluan += binhLuanModel1.getListHinhBinhLuan().size();
                tongdiem += binhLuanModel1.getChamdiem();

            }
            double diemtrungbinh = tongdiem / quanAnModel.getBinhLuanModelList().size();

            holder.txtTongHinhBinhLuan.setText(tongsohinhbinhluan + "");
            holder.txtDiemTrungbinh.setText(String.format("%.1f", diemtrungbinh));
            //end tổng bình luận và điểm trung bình

        } else {
            holder.txtDiemTrungbinh.setText("0");
            holder.txtTotalBinhLuan.setText("0");
            holder.txtTongHinhBinhLuan.setText("0");
            holder.containerBinhLuan1.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
        }
        //end binh luận
        //tổng bình luận
        holder.txtTotalBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        //end tổng bình luận


        //gán địa chỉ và khoảng cách gần nhất

        if (quanAnModel.getChiNhanhQuanAnModelList().size() > 0) {

            int vitrigannhat = viTriGanNhat(quanAnModel.getChiNhanhQuanAnModelList());
            holder.txtDiaChiQuanAnODau.setText(quanAnModel.getChiNhanhQuanAnModelList().get(vitrigannhat).getDiachi());
            Log.d("k", String.format("%.1f", quanAnModel.getChiNhanhQuanAnModelList().get(vitrigannhat).getKhoangcach()) + " km");
            holder.txtKhoangCach.setText(String.format("%.1f", quanAnModel.getChiNhanhQuanAnModelList().get(vitrigannhat).getKhoangcach()) + " km");

        }


        holder.cardViewQuanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                iChiTietQuanAn.putExtra("quanan", quanAnModel);
                context.startActivity(iChiTietQuanAn);
            }
        });


    }

    private int viTriGanNhat(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelLists) {
        int gannhat = 0;
        for (int i = 1; i < chiNhanhQuanAnModelLists.size(); i++) {
            if (chiNhanhQuanAnModelLists.get(i).getKhoangcach() < chiNhanhQuanAnModelLists.get(gannhat).getKhoangcach()) {
                gannhat = i;
            }
        }

        return gannhat;
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


    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ReadMoreTextView txtNoiDungBinhLuan2, txtNoiDungBinhLuan1;
        TextView txtTenQuanAnODau, txtDiaChiQuanAnODau, txtTieuDeBinhLuan1,
                txtTieuDeBinhLuan2, txtDiemBinhLuan1, txtDiemBinhLuan2, txtTotalBinhLuan,
                txtTongHinhBinhLuan, txtDiemTrungbinh, txtKhoangCach;
        CircleImageView circleImageView1, circleImageView2;
        Button btnDatMonODau;
        ImageView imgHinhQuanAn;
        CardView cardViewQuanAn;
        LinearLayout containerBinhLuan1, containerBinhLuan2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewQuanAn = itemView.findViewById(R.id.cardViewQuanAn);
            txtTenQuanAnODau = itemView.findViewById(R.id.txtTenQuanAnOdau);
            btnDatMonODau = itemView.findViewById(R.id.btnDatMonODau);
            imgHinhQuanAn = itemView.findViewById(R.id.imgHinhQuanAn);
            txtTieuDeBinhLuan1 = itemView.findViewById(R.id.txtTieuDeBinhLuan1);
            txtTieuDeBinhLuan2 = itemView.findViewById(R.id.txtTieuDeBinhLuan2);
            txtNoiDungBinhLuan1 = itemView.findViewById(R.id.txtNoiDungBinhLuan1);
            txtNoiDungBinhLuan2 = itemView.findViewById(R.id.txtNoiDungBinhLuan2);
            circleImageView1 = itemView.findViewById(R.id.cicleImageUser1);
            circleImageView2 = itemView.findViewById(R.id.cicleImageUser2);
            containerBinhLuan1 = itemView.findViewById(R.id.containerBinhLuan1);
            containerBinhLuan2 = itemView.findViewById(R.id.containerBinhLuan2);
            txtDiemBinhLuan1 = itemView.findViewById(R.id.txtDiemBinhLuan1);
            txtDiemBinhLuan2 = itemView.findViewById(R.id.txtDiemBinhLuan2);
            txtTotalBinhLuan = itemView.findViewById(R.id.txtTotalBinhLuan);
            txtTongHinhBinhLuan = itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtDiemTrungbinh = itemView.findViewById(R.id.txtDiemTrungBinh);
            txtDiaChiQuanAnODau = itemView.findViewById(R.id.txtDiaChiQuanAnODau);
            txtKhoangCach = itemView.findViewById(R.id.txtKhoangCach);
        }
    }
}
