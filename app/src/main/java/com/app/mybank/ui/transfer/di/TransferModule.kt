package com.app.mybank.ui.transfer.di

import com.app.mybank.core.scope.AuthRetrofit
import com.app.mybank.ui.transfer.service.TransferApi
import com.app.mybank.ui.transfer.service.TransferRepo
import com.app.mybank.ui.transfer.service.TransferRepoImpl
import com.app.mybank.ui.usecase.AppResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object TransferModule {
    @Provides
    @ViewModelScoped
    fun provideTransferApi(
        @AuthRetrofit retrofit: Retrofit
    ): TransferApi = retrofit.create(TransferApi::class.java)

    @Provides
    @ViewModelScoped
    fun provideTransferRepo(
        appResource: AppResource,
        api: TransferApi
    ): TransferRepo = TransferRepoImpl(
        appResource = appResource,
        api = api
    )
}