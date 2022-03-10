package com.app.mybank.ui.signup.di

import com.app.mybank.core.scope.BasicRetrofit
import com.app.mybank.ui.signup.service.SignUpApi
import com.app.mybank.ui.signup.service.SignUpRepo
import com.app.mybank.ui.signup.service.SignUpRepoImpl
import com.app.mybank.ui.usecase.AppResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SignUpModule {
    @Provides
    @ViewModelScoped
    fun provideSignupApi(
        @BasicRetrofit retrofit: Retrofit
    ): SignUpApi = retrofit.create(SignUpApi::class.java)

    @Provides
    @ViewModelScoped
    fun provideSignUpRepo(
        appResource: AppResource,
        api: SignUpApi
    ): SignUpRepo = SignUpRepoImpl(
        appResource = appResource,
        api = api
    )
}