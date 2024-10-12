package com.example.t2_italo_espinoza.service;

import com.example.t2_italo_espinoza.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("products")
    Call<List<Product>> LISTA_DE_PRODUCTOS();
}