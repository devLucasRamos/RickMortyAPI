package com.devLucasRamos.RickMortyAPI;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarPersonagens();
    }

    private void carregarPersonagens() {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<CharacterResponse> call = apiService.getCharacters();

        call.enqueue(new Callback<CharacterResponse>() {

            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    CharacterAdapter adapter = new CharacterAdapter(response.body().getResults());
                    recyclerView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(MainActivity.this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable throwable) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Falha: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}