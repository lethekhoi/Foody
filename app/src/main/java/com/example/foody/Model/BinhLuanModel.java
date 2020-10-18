package com.example.foody.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

public class BinhLuanModel implements Parcelable {
    String mauser;
    int chamdiem, luotthich;
    String noidung;
    String tieude;
    String mabinhluan;
    ThanhVienModel thanhVienModel;
    List<String> listHinhBinhLuan;


    protected BinhLuanModel(Parcel in) {
        mauser = in.readString();
        chamdiem = in.readInt();
        luotthich = in.readInt();
        noidung = in.readString();
        tieude = in.readString();
        mabinhluan = in.readString();
        listHinhBinhLuan = in.createStringArrayList();
        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }


    public List<String> getListHinhBinhLuan() {
        return listHinhBinhLuan;
    }

    public void setListHinhBinhLuan(List<String> listHinhBinhLuan) {
        this.listHinhBinhLuan = listHinhBinhLuan;
    }

    public BinhLuanModel() {
    }

    public BinhLuanModel(int chamdiem, int luotthich, String noidung, String tieude, ThanhVienModel thanhVienModel) {
        this.chamdiem = chamdiem;
        this.luotthich = luotthich;
        this.noidung = noidung;
        this.tieude = tieude;
        this.thanhVienModel = thanhVienModel;
    }

    public int getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(int chamdiem) {
        this.chamdiem = chamdiem;
    }

    public int getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(int luotthich) {
        this.luotthich = luotthich;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mauser);
        parcel.writeInt(chamdiem);
        parcel.writeInt(luotthich);
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mabinhluan);
        parcel.writeStringList(listHinhBinhLuan);
        parcel.writeParcelable(thanhVienModel, i);
    }

    public void ThemBinhLuan(String maquanan, BinhLuanModel binhLuanModel, List<Uri> uriList) {
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String mabinhluan = nodeBinhLuan.child(maquanan).push().getKey();

        nodeBinhLuan.child(maquanan).child(mabinhluan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (uriList.size() > 0) {
                        for (Uri uri : uriList) {
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhquanan/" + uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                }
                            });
                        }
                    }
                }
            }
        });

        if (uriList.size() > 0) {
            for (Uri uri : uriList) {
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());
            }
        }
    }
}
