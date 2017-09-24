package net.kusnadi.rtnetapps.service;

import net.kusnadi.rtnetapps.entity.LoginRequest;
import net.kusnadi.rtnetapps.entity.LoginResponse;
import net.kusnadi.rtnetapps.entity.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by root on 14/09/17.
 */

public interface UserService {
    @GET("/users")
    Call<UserResponse> getUsers();

    @POST("/users/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}
