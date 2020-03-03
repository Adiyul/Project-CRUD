package com.belajar.adi.kontak.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2/25/2020.
 */

public class User {

    @SerializedName("ktp")
    @Nullable
    @Expose
    private String ktp;

    @SerializedName("nama")
    @Nullable
    @Expose
    private String nama;

    @SerializedName("umur")
    @Nullable
    @Expose
    private int    umur;

    @SerializedName("jenis_kelamin")
    @Nullable
    @Expose
    private String jenis_kelamin;

    @Nullable
    public String getKtp() {
        return ktp;
    }

    public void setKtp(@Nullable String ktp) {
        this.ktp = ktp;
    }

    @Nullable
    public String getNama() {
        return nama;
    }

    public void setNama(@Nullable String nama) {
        this.nama = nama;
    }

    @Nullable
    public int getUmur() {
        return umur;
    }

    public void setUmur(@Nullable int umur) {
        this.umur = umur;
    }

    @Nullable
    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(@Nullable String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }
}
