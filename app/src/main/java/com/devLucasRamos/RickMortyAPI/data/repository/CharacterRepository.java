package com.devLucasRamos.RickMortyAPI.data.repository;

import com.devLucasRamos.RickMortyAPI.data.model.CharacterResponse;
import com.devLucasRamos.RickMortyAPI.data.remote.ApiService;
import com.devLucasRamos.RickMortyAPI.data.remote.RetrofitClient;

import retrofit2.Call;

public class CharacterRepository {

    private final ApiService apiService;

    public CharacterRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    public Call<CharacterResponse> getCharacters(int page) {
        return apiService.getCharacters(page);
    }
}