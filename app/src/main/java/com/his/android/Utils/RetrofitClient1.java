package com.his.android.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient1 {
    public static final String BASE_URL = "http://182.156.200.179:602/api/";
//    public static final String BASE_URL = "http://192.168.7.13:211/api/";
//    public static final String BASE_URL = "http://182.156.200.178:211/api/";
    //public static final String BASE_URL = "http://182.156.200.178:602/api/";
    private static RetrofitClient1 mInstance;
    private Retrofit retrofit;
    private RetrofitClient1() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(
                        chain -> {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .method(original.method(), original.body());
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                ).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized RetrofitClient1 getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient1();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
