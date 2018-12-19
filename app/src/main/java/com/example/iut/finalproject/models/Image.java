package com.example.iut.finalproject.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image implements Serializable {
    public String getFile() {
        return file;
    }

    @SerializedName("file")
    private String file;
}
