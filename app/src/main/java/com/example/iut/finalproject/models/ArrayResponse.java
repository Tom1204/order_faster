package com.example.iut.finalproject.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ArrayResponse<T> {

    @SerializedName("results")
    public List<T> list = new ArrayList<>();

    @SerializedName("next")
    public String nextPage;

    @SerializedName("previous")
    public String prevPage;

    @SerializedName("count")
    public int count;
}
