package com.example.foody.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TienIchModel {
    DatabaseReference dataRoot;
    String tentienich, hinhtienich, matienich;

    public String getTentienich() {
        return tentienich;
    }

    public String getHinhtienich() {
        return hinhtienich;
    }

    public void setHinhtienich(String hinhtienich) {
        this.hinhtienich = hinhtienich;
    }

    public String getMatienich() {
        return matienich;
    }

    public void setMatienich(String matienich) {
        this.matienich = matienich;
    }

    public void setTentienich(String tentienich) {
        this.tentienich = tentienich;
    }

    public TienIchModel() {
        dataRoot = FirebaseDatabase.getInstance().getReference();
    }

    public TienIchModel(String tentienich, String hinhtienich, String matienich) {
        this.tentienich = tentienich;
        this.hinhtienich = hinhtienich;
        this.matienich = matienich;
    }

}
