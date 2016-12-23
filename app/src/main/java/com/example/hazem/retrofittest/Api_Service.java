package com.example.hazem.retrofittest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Hazem on 12/23/2016.
 */
public interface Api_Service {

    @POST("register")
    Call<RegisterNewUser> RegisterUser(@Body MainActivity.RegisterRequest registerRequest);

}




