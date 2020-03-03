package com.belajar.adi.kontak.presenter;

import com.belajar.adi.kontak.GlobalConsta;
import com.belajar.adi.kontak.service.UserService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2/25/2020.
 */

public class Presenter {

    //private String port; // port dipake ketika lu mau pake dev atau prod
    private Retrofit retrofit;
    //bikin method Retrofit builder nya
    public Retrofit retrofitBuilder(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();
        //initiate base url
        // INI DI PAKE kalo lu pake port
        /** start prort **/
//        if (GlobalConsta.APP_TYPE_LAUNCH.equalsIgnoreCase("0")) {
//            port = GlobalConstants.APP_PORT_DEV;
//        } else {
//            port = GlobalConstants.APP_PORT_PROD;
//        }
//        String BASE_URL = String.format("http://202.169.43.53:%s/", port);
    /** end port **/

        retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConsta.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    //disini kita bakal bikin method buat init Service
    public UserService userService(){
        return retrofitBuilder().create(UserService.class);
    }


}
