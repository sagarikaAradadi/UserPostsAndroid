package com.samplerecyclerview.ui.di

import android.app.Application
import android.content.Context
import com.conduent.nationalhighways.data.error.mapper.ErrorMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.samplerecyclerview.BuildConfig
import com.samplerecyclerview.ui.data.retrofit.PostsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import java.util.logging.ErrorManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    internal fun provideContext(application: Application): Context {
        return application
    }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpAuthKey())
        .build()

    private fun httpAuthKey(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain: Interceptor.Chain ->
            val authorisedReq = chain.request().newBuilder()
                .build()
            chain.proceed(authorisedReq)
        }
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }



    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providePostServicesApis(retrofit: Retrofit): PostsApiService =
        retrofit.create(PostsApiService::class.java)


}