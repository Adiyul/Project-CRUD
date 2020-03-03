package com.belajar.adi.kontak.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2/25/2020.
 */

public class Value {

    @SerializedName("error")
    private boolean error;
    @SerializedName("error_message")
    private String error_message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
