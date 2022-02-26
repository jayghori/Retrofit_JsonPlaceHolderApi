package com.example.retrofit_jsonplaceholderapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofit_jsonplaceholderapi.adapter.PostAdapter;
import com.example.retrofit_jsonplaceholderapi.model.post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<post> postList;
    PostAdapter postAdapter;
    JsonPlaceApiService jsonPlaceApiService;
    String TAG = "TAGER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        postList=new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceApiService = retrofit.create(JsonPlaceApiService.class);

//        GetAllPost();
        GetPostById();


    }

    private void GetPostById() {

        Call<post> call=jsonPlaceApiService.getPostById("1");

        call.enqueue(new Callback<post>() {
            @Override
            public void onResponse(Call<post> call, Response<post> response) {
                post posts=response.body();
                postList.add(posts);

                postAdapter=new PostAdapter(postList,MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                recyclerView.setAdapter(postAdapter);
                
            }

            @Override
            public void onFailure(Call<post> call, Throwable t) {

            }
        });

    }

    private void GetAllPost() {

        Call<List<post>> call = jsonPlaceApiService.getAllPost();
        call.enqueue(new Callback<List<post>>() {
            @Override
            public void onResponse(Call<List<post>> call, Response<List<post>> response) {

                if(response.isSuccessful()){
                    List<post> posts=response.body();

                    for (post post:posts){
                        postList.add(post);
                    }
                    postAdapter=new PostAdapter(postList,MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(postAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<post>> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }
}