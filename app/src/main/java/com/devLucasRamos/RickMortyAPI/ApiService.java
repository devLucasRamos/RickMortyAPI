package com.devLucasRamos.RickMortyAPI;

import retrofit2.Call;
import retrofit2.http.GET;
public interface ApiService {
    @GET("character")
    Call<CharacterResponse> getCharacters();
}
