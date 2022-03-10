package com.app.mybank.core.module

import com.app.mybank.BuildConfig
import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.scope.*
import com.app.mybank.core.util.NetworkConstant
import com.app.mybank.core.util.PreferenceKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @BasicRetrofit
    fun provideBasicRetrofitClient(
        gson: Gson,
        httpClient: OkHttpClient
    ): Retrofit {
        return provideRetrofit(gson = gson, httpClient = httpClient)
    }

    @Provides
    @AuthRetrofit
    fun provideAuthenticatedRetrofit(
        @AuthInterceptor interceptor: Interceptor,
        gson: Gson,
        httpClient: OkHttpClient
    ): Retrofit {
        val newHttpClient = httpClient
            .newBuilder()
            .addInterceptor(interceptor)
            .build()
        return provideRetrofit(gson = gson, httpClient = newHttpClient)
    }

    @Provides
    fun provideHttpClient(
        @HttpLogger interceptor: Interceptor,
        @DefaultHeader defaultHeader: Interceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(defaultHeader)
        httpClient.addInterceptor(interceptor)
        return httpClient.build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @HttpLogger
    @Provides
    fun provideLogger(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @AuthInterceptor
    @Provides
    fun provideAuthInterceptor(dataStore: DataStore): Interceptor {
        val authInterceptor = Interceptor {
            val request = it.request().newBuilder()
            request.header(
                NetworkConstant.AUTH,
                dataStore.getString(PreferenceKey.TOKEN, "") ?: ""
            )
            val newRequest = request.build()
            it.proceed(newRequest)
        }
        return authInterceptor
    }

    @DefaultHeader
    @Provides
    fun provideDefaultHeader(): Interceptor {
        val authInterceptor = Interceptor {
            val request = it.request().newBuilder()
            request.header(
                NetworkConstant.CONTENT_TYPE,
                NetworkConstant.APPLICATION_JSON,
            )
            request.header(
                NetworkConstant.ACCEPT_TYPE,
                NetworkConstant.APPLICATION_JSON,
            )
            val newRequest = request.build()
            it.proceed(newRequest)
        }
        return authInterceptor
    }

    private fun provideRetrofit(
        gson: Gson,
        httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}