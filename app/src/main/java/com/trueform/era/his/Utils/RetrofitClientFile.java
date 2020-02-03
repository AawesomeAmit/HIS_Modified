package com.trueform.era.his.Utils;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientFile {
    //Live
    public static final String BASE_URL = "http://182.156.200.179:201/";

    //public static final String BASE_URL = "http://182.156.200.178:229/api/";
    //public static final String BASE_URL = "http://192.168.7.13:229/api/";

    //Local
//    public static final String BASE_URL = "http://192.168.7.13:229/";
    private static RetrofitClientFile mInstance;


    private Retrofit retrofit;
    private RetrofitClientFile() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(
                        /*chain -> {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .method(original.method(), original.body());
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }*/
                        logging
                ).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized RetrofitClientFile getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClientFile();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
