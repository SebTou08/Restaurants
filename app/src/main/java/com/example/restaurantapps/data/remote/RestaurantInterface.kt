package com.example.restaurantapps.data.remote


import com.example.restaurantapps.data.entities.Restaurant
import retrofit2.Call
import retrofit2.http.GET

interface RestaurantInterface {

    @GET("restaurants")
    fun fetchRestaurants(): Call<List<Restaurant>>
}