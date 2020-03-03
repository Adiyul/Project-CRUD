package com.belajar.adi.kontak;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.belajar.adi.kontak.model.Value;
import com.belajar.adi.kontak.presenter.Presenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateActivity extends AppCompatActivity {

    private Presenter mPresenter;
    private String ktp;
    private String nama;
    private int umur;
    private String jenis_kelamin;
    private EditText ktpEd;
    private EditText umurEd;
    private EditText jenisKelaminEd;
    private  EditText namaEd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Button btnUpdate = findViewById(R.id.buttonUbah);
        initDataField();
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                initDataField();
                mPresenter = new Presenter();
                Call<Value> result = mPresenter.userService().ubahUser(ktp, nama, umur, jenis_kelamin); // ini kan proses koneksi ke api
                result.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if (response.body().isError()) {
                            Toast.makeText(getApplicationContext(), "Tambah Data Gagal", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Tambah Data Berhasil", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Tambah Data Gagal", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Peringatan");
                alertDialogBuilder
                        .setMessage("Apakah Anda yakin ingin mengapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {

                                mPresenter = new Presenter();
                                Call<Value> result = mPresenter.userService().hapusUser(ktp);
                                result.enqueue(new Callback<Value>() {

                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        if (response.body().isError()) {
                                            Toast.makeText(getApplicationContext(), "Hapus Data Gagal", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Hapus Data Berhasil", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
                default: finish();break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDataField() {

        // disni lu terima data yg dari item yg di click tadi

        ktpEd = findViewById(R.id.edt_ktp);
        namaEd = findViewById(R.id.edt_nama);
        umurEd = findViewById(R.id.edt_umur);
        jenisKelaminEd = findViewById(R.id.edt_jenis_kelamin);

        ktpEd.setText(getIntent().getStringExtra("ktp"));
        namaEd.setText(getIntent().getStringExtra("nama"));
        //umurEd.setText(getIntent().getStringExtra("umur")); // nah ini berlaku untuk tipe data string
        //kalo tipe data nya int
        umurEd.setText(String.valueOf(getIntent().getIntExtra("umur",0))); // ini pake ini untuk yg integer
        jenisKelaminEd.setText(getIntent().getStringExtra("jenis_kelamin"));
        System.out.println("Ktp : "+ktpEd.getText());
        ktp = ktpEd.getText().toString();
        nama = namaEd.getText().toString();
        umur = Integer.valueOf(umurEd.getText().toString());
        jenis_kelamin = jenisKelaminEd.getText().toString();
    }


}