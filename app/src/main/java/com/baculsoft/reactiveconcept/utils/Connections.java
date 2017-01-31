package com.baculsoft.reactiveconcept.utils;

import com.baculsoft.reactiveconcept.internal.Api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public final class Connections {
    private static volatile Connections INSTANCE = null;

    public static synchronized Connections get() {
        if (INSTANCE == null) {
            INSTANCE = new Connections();
        }
        return INSTANCE;
    }

    public Api getApi() {
        final Retrofit retrofit = new Retrofit.Builder().client(getOkHttpClient())
                                                        .baseUrl(Constants.Api.BASE_URL)
                                                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                                        .addConverterFactory(JacksonConverterFactory.create())
                                                        .build();

        return retrofit.create(Api.class);
    }

    private OkHttpClient getOkHttpClient() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                                         .readTimeout(15, TimeUnit.SECONDS)
                                         .writeTimeout(15, TimeUnit.SECONDS)
                                         .retryOnConnectionFailure(true)
                                         .addInterceptor(getInterceptor())
                                         .addInterceptor(httpLoggingInterceptor)
                                         .build();
    }

    private Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(final Chain chain) throws IOException {
                final Request request = chain.request();
                final Request.Builder builder = request.newBuilder().addHeader("Content-Type", "application/json");

                return chain.proceed(builder.build());
            }
        };
    }
}