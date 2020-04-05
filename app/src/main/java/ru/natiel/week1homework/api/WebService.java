package ru.natiel.week1homework.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {
    private static volatile WebService instance;
    private Api api;

    public static WebService getInstance() {
        WebService localInstance = instance;
        if (localInstance == null) {
            synchronized (WebService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new WebService();
                }
            }
        }
        return localInstance;
    }

    private WebService(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://loftschool.com/android-api/basic/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
