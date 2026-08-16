package com.devLucasRamos.RickMortyAPI;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvPage;
    private Button btnPrevious, btnNext;

    private CharacterAdapter adapter;
    private List<Character> characterList = new ArrayList<>();

    private int currentPage = 1;
    private int totalPages = 1;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        tvPage = findViewById(R.id.tvPage);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CharacterAdapter(characterList);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        carregarPersonagens(currentPage);

        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                carregarPersonagens(currentPage);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentPage < totalPages) {
                currentPage++;
                carregarPersonagens(currentPage);
            }
        });
    }

    private void carregarPersonagens(int page) {
        progressBar.setVisibility(View.VISIBLE);

        Call<CharacterResponse> call = apiService.getCharacters(page);

        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    CharacterResponse body = response.body();

                    if (body.getInfo() != null) {
                        totalPages = body.getInfo().getPages();
                    }

                    characterList.clear();
                    if (body.getResults() != null) {
                        characterList.addAll(body.getResults());
                    }
                    adapter.notifyDataSetChanged();

                    tvPage.setText("Página " + currentPage + " de " + totalPages);

                    btnPrevious.setEnabled(currentPage > 1);
                    btnNext.setEnabled(currentPage < totalPages);

                    recyclerView.scrollToPosition(0);
                } else {
                    Toast.makeText(MainActivity.this, "Sinal Interdimensional Perdido no Terminal da Cidadela, Tente novamente!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Bloqueio de Sinal do Conselho dos Ricks: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}