package com.belajar.adi.kontak.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.belajar.adi.kontak.R;
import com.belajar.adi.kontak.UpdateActivity;
import com.belajar.adi.kontak.model.User;
import com.belajar.adi.kontak.model.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2/25/2020.
 */

public class UserAdapters extends RecyclerView.Adapter<UserAdapters.MyViewHolder> {
    List<User> userList;

    public UserAdapters( List<User> userList) {
        this.userList = userList;
        System.out.println("User List : "+userList.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kontak_list, parent, false);
        return new UserAdapters.MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.mTextViewKtp.setText("Ktp = " + userList.get(position).getKtp());
        holder.mTextViewNama.setText("Nama = " + userList.get(position).getNama());
        holder.mTextViewUmur.setText("Umur = " + userList.get(position).getUmur());
        holder.mTextViewJenis_kelamin.setText("Jenis_kelamin = " + userList.get(position).getJenis_kelamin());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), userList.get(position).getKtp(), Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(view.getContext(), UpdateActivity.class);
                mIntent.putExtra("ktp", userList.get(position).getKtp());
                mIntent.putExtra("nama", userList.get(position).getNama());
                mIntent.putExtra("umur", Integer.valueOf(userList.get(position).getUmur()));
                mIntent.putExtra("jenis_kelamin", userList.get(position).getJenis_kelamin());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (userList != null ? userList.size(): 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewKtp, mTextViewNama, mTextViewUmur, mTextViewJenis_kelamin;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mTextViewKtp = itemView.findViewById(R.id.tvKtp);
            mTextViewNama = itemView.findViewById(R.id.tvNama);
            mTextViewUmur = itemView.findViewById(R.id.tvUmur);
            mTextViewJenis_kelamin = itemView.findViewById(R.id.tvJenis_kelamin);


        }
    }
}
