package com.belajar.adi.kontak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.belajar.adi.kontak.adapter.UserAdapters;
import com.belajar.adi.kontak.model.User;
import com.belajar.adi.kontak.model.UserList;
import com.belajar.adi.kontak.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btIns, btUbh;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapters mUserAdapters;
    private List<User> mUserList = new ArrayList<>();
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init list via retrofit
        initList();
        btIns = findViewById(R.id.btIns);
        btIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });

        btUbh = findViewById(R.id.btUbh);
        btUbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UpdateActivity.class));
            }
        });
    }

    private void initList(){
        //disini kita akan gunain retrofit buat konek ke api
        // init presenter
        mPresenter = new Presenter();
        //ini proses kita manggil ke endpoint getUser
        //terus di tampung di model User pake gini Call<User> result
        // nanti hasil yang di dapet itu di tampung di user dan di isi ke variable result
        final Call<UserList> result  = mPresenter.userService().ambilAllUser();
        result.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                //kita debug disini
                System.out.println("Repsonse : "+response.body().toString());

                // nah didalem sini ketika response nya berhasil,
                //kita bisa apain aja datanya di dalem sini
                if(response.body() != null || response.body().getUserList().isEmpty()){
                    // nah dengan begini list user udah ke isi dengan data yg kita dapet dari datbase
                    // via endpoint getUser
                    mUserList = response.body().getUserList();
                    // initial recyclerview nya
                    mRecyclerView = findViewById(R.id.recyclerView);
                    //initial adapter nya
                    mUserAdapters = new UserAdapters(mUserList);
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(mUserAdapters);
                    mUserAdapters.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Data Kosong",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal Retrive data",Toast.LENGTH_LONG).show();
                //kita debug disini
                System.out.println("Gagal : "+t.getMessage());
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        initList();
    }
}
