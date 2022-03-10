package com.app.mybank.ui.login.di

import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.scope.BasicRetrofit
import com.app.mybank.ui.login.service.LoginApi
import com.app.mybank.ui.login.service.LoginRepo
import com.app.mybank.ui.login.service.LoginRepoImpl
import com.app.mybank.ui.usecase.AppResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    @ViewModelScoped
    fun provideLoginApi(
        @BasicRetrofit retrofit: Retrofit
    ): LoginApi = retrofit.create(LoginApi::class.java)

    @Provides
    @ViewModelScoped
    fun provideLoginRepo(
        appResource: AppResource,
        api: LoginApi,
        dataStore: DataStore
    ): LoginRepo = LoginRepoImpl(
        appResource = appResource,
        api = api,
        dataStore = dataStore
    )

}