package com.belajar.adi.kontak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.belajar.adi.kontak.model.Value;
import com.belajar.adi.kontak.presenter.Presenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertActivity extends AppCompatActivity {

    private Button btnInsert;
    private Presenter mPresenter;
    private String ktp;
    private String nama;
    private int umur;
    private String jenis_kelamin;

    private EditText ktpEd,namaEd,umurEd,jenisKelaminEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        btnInsert = findViewById(R.id.btInserting);
        //ini action buat button insert
        btnInsert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initDataField();
                mPresenter = new Presenter();
                Call<Value> result = mPresenter.userService().tambahUser(ktp,nama,umur,jenis_kelamin); // ini kan proses koneksi ke api
                result.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if(response.body().isError() == true){
                            Toast.makeText(getApplicationContext(),"Tambah Data Gagal",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Tambah Data Berhasil", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Tambah Data Gagal",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    private void initDataField(){
        ktpEd           = findViewById(R.id.field_ktp);
        namaEd          = findViewById(R.id.field_nama);
        umurEd          = findViewById(R.id.field_umur);
        jenisKelaminEd  = findViewById(R.id.field_jenis_kelamin);

        //assign nilai ke setiap variable
        ktp             = ktpEd.getText().toString();
        nama            = namaEd.getText().toString();
        umur            = Integer.valueOf(umurEd.getText().toString());
        jenis_kelamin   = jenisKelaminEd.getText().toString();
    }

}
