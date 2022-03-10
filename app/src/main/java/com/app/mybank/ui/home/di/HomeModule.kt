package com.app.mybank.ui.home.di

import com.app.mybank.core.scope.AuthRetrofit
import com.app.mybank.ui.home.service.HomeApi
import com.app.mybank.ui.home.service.HomeRepo
import com.app.mybank.ui.home.service.HomeRepoImpl
import com.app.mybank.ui.usecase.AppResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    @ViewModelScoped
    fun provideHomeApi(
        @AuthRetrofit retrofit: Retrofit
    ): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    @ViewModelScoped
    fun provideHomeRepo(
        appResource: AppResource,
        api: HomeApi
    ): HomeRepo = HomeRepoImpl(
        appResource = appResource,
        api = api
    )


}