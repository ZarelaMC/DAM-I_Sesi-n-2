package com.example.proyecto02.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {
    //Creación de la Connection

    public static final String URL = "https://jsonplaceholder.typicode.com/"; //Ruta al servicio REST
    public static Retrofit retrofit = null;
    public static Retrofit getConnecionRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
