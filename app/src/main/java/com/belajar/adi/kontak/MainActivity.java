package com.belajar.adi.kontak;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.MenuItem;


import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;

import android.widget.SearchView;
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

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapters mUserAdapters; //ini kan udah ada... bego banget w
    private List<User> mUserList = new ArrayList<>();
    private List<User> filteredUser = new ArrayList<>();

    private Presenter mPresenter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init list via retrofit
        initList();
        initRecyclerView();

    }


    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        //initial adapter nya
        mRecyclerView.setHasFixedSize(true);
        mUserAdapters = new UserAdapters(mUserList);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mUserAdapters);
    }

    private void initList() {
        //disini kita akan gunain retrofit buat konek ke api
        // init presenter
        mPresenter = new Presenter();
        //ini proses kita manggil ke endpoint getUser
        //terus di tampung di model User pake gini Call<User> result
        // nanti hasil yang di dapet itu di tampung di user dan di isi ke variable result
        final Call<UserList> result = mPresenter.userService().ambilAllUser();
        result.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                //kita debug disini
                System.out.println("Repsonse : " + response.body().toString());

                // nah didalem sini ketika response nya berhasil,
                //kita bisa apain aja datanya di dalem sini
                if (response.body() != null || response.body().getUserList().isEmpty()) {
                    // nah dengan begini list user udah ke isi dengan data yg kita dapet dari datbase
                    // via endpoint getUser
                    mUserList.clear();
                    mUserList = response.body().getUserList();
                    initRecyclerView();
                    mUserAdapters.notifyDataSetChanged();
                    // initial recyclerview nya
                } else {
                    Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal Retrive data", Toast.LENGTH_LONG).show();
                //kita debug disini
                System.out.println("Gagal : " + t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.menus,menu);
         return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert:
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
                break;
            case R.id.menuSearch:
                searchView = (SearchView) item.getActionView();
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mUserAdapters.getFilter().filter(query);
                        initRecyclerView();
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String nextText) {
                        //Data akan berubah saat user menginputkan text/kata kunci pada SearchView
                        mUserAdapters.getFilter().filter(nextText);
                        System.out.println("User List 1 : " + mUserList.size());
                        System.out.println("User List 2 : " + filteredUser.size());
                        initRecyclerView();
                        return false;
                    }
                });
                return true;
            default:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


