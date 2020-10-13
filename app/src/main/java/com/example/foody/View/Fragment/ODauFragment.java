package com.example.foody.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.foody.Controller.ODauController;
import com.example.foody.Model.QuanAnModel;
import com.example.foody.R;

import java.util.List;


public class ODauFragment extends Fragment {
    RecyclerView recyclerViewODau;
    ODauController oDauController;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau, container, false);
        recyclerViewODau = view.findViewById(R.id.recyclerViewODau);
        progressBar = view.findViewById(R.id.ODauProgressBar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        oDauController = new ODauController(getContext(), vitrihientai);
        oDauController.getDanhSachQuanAnController(recyclerViewODau, progressBar);

    }
}