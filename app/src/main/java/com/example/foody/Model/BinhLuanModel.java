package com.example.foody.Model;

import java.util.List;

public class BinhLuanModel {
    String mauser;
    int chamdiem, luotthich;
    String noidung;
    String tieude;
    String mabinhluan;
    ThanhVienModel thanhVienModel;
    List<String> listHinhBinhLuan;

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
}
