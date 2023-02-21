package com.example.minggu3;

public class MahasiswaModel {
    private String naam, nim, nohp;

    public MahasiswaModel(String naam, String nim, String nohp) {
        this.naam = naam;
        this.nim = nim;
        this.nohp = nohp;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }
}
