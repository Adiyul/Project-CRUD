package com.belajar.adi.kontak.service;

import com.belajar.adi.kontak.model.User;
import com.belajar.adi.kontak.model.UserList;
import com.belajar.adi.kontak.model.Value;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lenovo on 2/25/2020.
 */

public interface UserService {
    //endpoint buat ambil atau retrieve(ambil data dari database) user kan cuma getUser($ktp)
    @POST("user/getUser")
    Call<User> ambilDataUser(); // ini berlaku untuk ambil satu data user

    @POST("user/getUser")
    Call<UserList> ambilAllUser(); // ini berlaku untuk ambil semua data user, makanya butuh list

    //kita bikin service buat add ke database
    @FormUrlEncoded
    @POST("user/add")
    Call<Value> tambahUser(@Field("ktp") String ktp,
                           @Field("nama") String nama,
                           @Field("umur") int umur,
                           @Field("jenis_kelamin") String jenis_kelamin);

    @FormUrlEncoded
    @POST("user/update")
    Call<Value> ubahUser (@Field("ktp") String ktp,
                           @Field("nama") String nama,
                           @Field("umur") int umur,
                           @Field("jenis_kelamin") String jenis_kelamin);

    @FormUrlEncoded
    @POST("user/delete")
    Call<Value> hapusUser (@Field("ktp") String ktp);


}
