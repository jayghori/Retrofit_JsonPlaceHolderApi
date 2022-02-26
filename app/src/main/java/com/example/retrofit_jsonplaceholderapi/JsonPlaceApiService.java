package com.example.retrofit_jsonplaceholderapi;

import com.example.retrofit_jsonplaceholderapi.model.post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceApiService {

    @GET("posts")
    Call<List<post>>  getAllPost();

    @GET("posts/{id}")
    Call<post> getPostById(@Path("id") String postid);
}
