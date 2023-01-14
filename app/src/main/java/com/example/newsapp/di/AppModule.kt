package com.example.newsapp.di


import androidx.viewbinding.BuildConfig
import com.example.newsapp.api.NewsAPI
import com.example.newsapp.repositories.MainRemoteRepo
import com.example.newsapp.utils.Constants.API_KEY
import com.example.newsapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

//    @Singleton
//    @Provides
//    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        val requestInterceptor = Interceptor { chain ->
//            val url = chain.request()
//                .url
//                .newBuilder()
//                .addQueryParameter("api_key", API_KEY)
//                .build()
//
//            val request = chain.request()
//                .newBuilder()
//                .url(url)
//                .build()
//            return@Interceptor chain.proceed(request)
//        }
//
//        OkHttpClient
//            .Builder()
//            .addInterceptor(requestInterceptor)
//            .addInterceptor(loggingInterceptor)
//            .build()
//    } else {
//        OkHttpClient
//            .Builder()
//            .build()
//    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepo(api: NewsAPI): MainRemoteRepo {
        return MainRemoteRepo(api)
    }

}