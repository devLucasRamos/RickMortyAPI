package com.devLucasRamos.RickMortyAPI.data.remote;

import com.devLucasRamos.RickMortyAPI.data.model.CharacterResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("character")
    Call<CharacterResponse> getCharacters(@Query("page") int page);
}
