package net.kusnadi.rtnetapps.helper;

import net.kusnadi.rtnetapps.config.AppConfig;
import net.kusnadi.rtnetapps.service.UserService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 14/09/17.
 */

public class RetrofitClientHelper {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static UserService getApiService() {
        return RetrofitClientHelper.getClient(AppConfig.API_URL).create(UserService.class);
    }
}
