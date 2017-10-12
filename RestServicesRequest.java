package com.bestdb;

import com.bestdb.model.AccessTokenResponse;
import com.bestdb.model.InstagramRequiestResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Hitesh on 10/11/2017.
 */

public class RestServicesRequest {

    private static ConsumerApiInterface consumerApiInterface;

    public static ConsumerApiInterface getClient() {
        if (consumerApiInterface == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okClient = new OkHttpClient.Builder().addInterceptor(logging).build();

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").setLenient().create();

            Retrofit client = new Retrofit.Builder().baseUrl(Utility.BASE_URL).client(okClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
            consumerApiInterface = client.create(ConsumerApiInterface.class);
        }
        return consumerApiInterface;
    }


    public interface ConsumerApiInterface {
        @Headers("Content-Type: application/json")
        @FormUrlEncoded
        @POST("oauth/authorize/?")
        Call<AccessTokenResponse> getAccessToken(@Field("client_id") String client_id,
                                                 @Field("client_secret") String client_secret,
                                                 @Field("redirect_uri") String redirect_uri,
                                                 @Field("grant_type") String response_type,
                                                 @Field("code") String code);

        @Headers("Content-Type: application/json")
        @FormUrlEncoded
        @POST("v1/users/self/media/recent?")
        Call<InstagramRequiestResponse> getImages(@Field("access_token") String access_token);

    }
}
