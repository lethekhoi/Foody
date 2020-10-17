package com.example.foody.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Model.QuanAnModel;
import com.example.foody.Model.WifiQuanAnModel;
import com.example.foody.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterDanhSachWifi extends RecyclerView.Adapter<AdapterDanhSachWifi.ViewHolder> {
    List<WifiQuanAnModel> wifiQuanAnModelList;
    int resource;
    Context context;

    public AdapterDanhSachWifi(List<WifiQuanAnModel> wifiQuanAnModelList, int resource, Context context) {
        this.wifiQuanAnModelList = wifiQuanAnModelList;
        this.resource = resource;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new AdapterDanhSachWifi.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WifiQuanAnModel wifiQuanAnModel = wifiQuanAnModelList.get(position);
        holder.txtTenWifi.setText(wifiQuanAnModel.getTen());
        String matkhauText = getColoredSpanned("Mật khẩu", "#9c9a9a");
        String matkhau = getColoredSpanned(wifiQuanAnModel.getMatkhau(), "#030bfc");
        matkhau = "<i>" + matkhau + "</i>";
        holder.txtMatKhauWifi.setText(Html.fromHtml(matkhauText + " - " + matkhau));
        holder.txtNgayDangWifi.setText(wifiQuanAnModel.getNgaydang())   ;
    }

    @Override
    public int getItemCount() {
        return wifiQuanAnModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenWifi, txtMatKhauWifi, txtNgayDangWifi;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNgayDangWifi = itemView.findViewById(R.id.txtNgayDangWifi);
            txtTenWifi = itemView.findViewById(R.id.tenWifi);
            txtMatKhauWifi = itemView.findViewById(R.id.matkhauwifi);
        }
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}
