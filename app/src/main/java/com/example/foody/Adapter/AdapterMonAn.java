package com.example.foody.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Model.DatMon;
import com.example.foody.Model.MonAnModel;
import com.example.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.ViewHolder> {
    final long ONE_MEGABYTE = 1024 * 1024;
    Context context;
    List<MonAnModel> monAnModelList;
    public static List<DatMon> datMonList;
    FirebaseStorage firebaseStorage;

    public AdapterMonAn(Context context, List<MonAnModel> monAnModelList) {
        this.context = context;
        this.monAnModelList = monAnModelList;
        firebaseStorage = FirebaseStorage.getInstance();
        datMonList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_monan, parent, false);

        return new AdapterMonAn.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSoLuongMonAn.setTag(0);
        MonAnModel monAnModel = monAnModelList.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());
        holder.txtGiaMonAn.setText(monAnModel.getGiatien() + " đ");
        setHinhAnhBinhLuan(holder.imageViewHinhMonAn, monAnModel.getHinhanh());

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = Integer.parseInt(holder.txtSoLuongMonAn.getTag().toString());
                dem++;
                holder.imgMinus.setVisibility(View.VISIBLE);
                holder.txtSoLuongMonAn.setTag(dem);
                holder.txtSoLuongMonAn.setText(dem + "");
                DatMon datMonTag = (DatMon) holder.imgMinus.getTag();
                if (datMonTag != null) {
                    AdapterMonAn.datMonList.remove(datMonTag);
                }

                DatMon datMon = new DatMon();
                datMon.setSoluong(dem);
                datMon.setMonAnModel(monAnModel);
                holder.imgMinus.setTag(datMon);
                AdapterMonAn.datMonList.add(datMon);
                for (DatMon datMon1 : AdapterMonAn.datMonList) {
                    Log.d("datmon", "plus " + datMon1.getMonAnModel().getTenmon() + "-" + datMon1.getSoluong());

                }
                Log.d("datmon", "plus----------------------------------");

            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = Integer.parseInt(holder.txtSoLuongMonAn.getTag().toString());
                DatMon datMonTag = (DatMon) holder.imgMinus.getTag();
                if (datMonTag != null) {
                    AdapterMonAn.datMonList.remove(datMonTag);
                }
                if (dem > 0) {
                    dem--;
                    if (dem == 0) {
                        holder.imgMinus.setVisibility(View.INVISIBLE);
                    } else {
                        DatMon datMon = new DatMon();
                        datMon.setSoluong(dem);
                        datMon.setMonAnModel(monAnModel);
                        holder.imgMinus.setTag(datMon);
                        AdapterMonAn.datMonList.add(datMon);

                    }
                    for (DatMon datMon1 : AdapterMonAn.datMonList) {
                        Log.d("datmon", "minus" + datMon1.getMonAnModel().getTenmon() + "-" + datMon1.getSoluong());
                    }
                    Log.d("datmon", "minus----------------------------------");
                }

                holder.txtSoLuongMonAn.setTag(dem);
                holder.txtSoLuongMonAn.setText(dem + "");

            }
        });


    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenMonAn, txtGiaMonAn, txtSoLuongMonAn;
        ImageView imageViewHinhMonAn, imgMinus, imgPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSoLuongMonAn = itemView.findViewById(R.id.txtSoLuongMonAn);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtGiaMonAn = itemView.findViewById(R.id.txtGiaMonAn);
            imageViewHinhMonAn = itemView.findViewById(R.id.imgHinhMonAn);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            imgPlus = itemView.findViewById(R.id.imgPlus);
        }
    }

    private void setHinhAnhBinhLuan(final ImageView imageView, String linkhinh) {
        StorageReference storageHinhanh = firebaseStorage.getReference().child("hinhquanan").child(linkhinh);
        storageHinhanh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
