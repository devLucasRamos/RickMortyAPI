package com.devLucasRamos.RickMortyAPI.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devLucasRamos.RickMortyAPI.R;
import com.devLucasRamos.RickMortyAPI.data.model.Character;
import com.devLucasRamos.RickMortyAPI.data.model.CharacterResponse;
import com.devLucasRamos.RickMortyAPI.data.repository.CharacterRepository;
import com.devLucasRamos.RickMortyAPI.ui.adapter.CharacterAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvPage;
    private Button btnPrevious, btnNext;

    private CharacterAdapter adapter;
    private List<Character> characterList = new ArrayList<>();

    private int currentPage = 1;
    private int totalPages = 1;

    private CharacterRepository repository;

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

        repository = new CharacterRepository();

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

        repository.getCharacters(page).enqueue(new Callback<CharacterResponse>() {
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