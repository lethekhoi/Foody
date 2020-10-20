package com.example.foody.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.Model.ThucDonModel;
import com.example.foody.R;

import java.util.List;

public class AdapterThucDon extends RecyclerView.Adapter<AdapterThucDon.ViewHolder> {
    Context context;
    List<ThucDonModel> thucDonModelList;

    public AdapterThucDon(Context context, List<ThucDonModel> thucDonModelList) {
        this.context = context;
        this.thucDonModelList = thucDonModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_thucdon, parent, false);

        return new AdapterThucDon.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThucDonModel thucDonModel = thucDonModelList.get(position);
        holder.txtTenThucDon.setText(thucDonModel.getTenThucDon());
        holder.recyclerViewMonAn.setLayoutManager(new LinearLayoutManager(context));
        AdapterMonAn adapterMonAn = new AdapterMonAn(context, thucDonModel.getMonAnModelList());
        holder.recyclerViewMonAn.setAdapter(adapterMonAn);
        adapterMonAn.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return thucDonModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenThucDon;
        RecyclerView recyclerViewMonAn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenThucDon = itemView.findViewById(R.id.txtTenThucDon);
            recyclerViewMonAn = itemView.findViewById(R.id.recyclerViewMonAn);
        }
    }
}
