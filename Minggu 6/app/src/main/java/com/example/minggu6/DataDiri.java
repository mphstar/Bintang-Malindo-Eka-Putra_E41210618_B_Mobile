package com.example.minggu6;

public class DataDiri {
    int idnya;
    String nama, alamat, hobi;

    public DataDiri(int id, String nama, String alamat, String hobi) {
        this.idnya = id;
        this.nama = nama;
        this.alamat = alamat;
        this.hobi = hobi;
    }

    public int getIdnya() {
        return idnya;
    }

    public void setIdnya(int id) {
        this.idnya = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHobi() {
        return hobi;
    }

    public void setHobi(String hobi) {
        this.hobi = hobi;
    }
}
