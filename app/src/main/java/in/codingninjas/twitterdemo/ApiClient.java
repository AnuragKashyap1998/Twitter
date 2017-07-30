package in.codingninjas.twitterdemo;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anurag on 30-07-2017.
 */

public class ApiClient {
    private static Retrofit retrofit;
    private static ApiInterface apiInterface;
    public static ApiInterface getInstance()
    {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.twitter.com/1.1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(MainActivity.getInterceptor())
                            .build())
                    .build();
            apiInterface = retrofit.create(ApiInterface.class);
        }
        return  apiInterface;
    }



}
