package com.app.mybank.core.module

import android.content.Context
import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.dao.DataStoreImpl
import com.app.mybank.core.util.*
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.ui.usecase.AppResourceImpl
import com.app.mybank.ui.usecase.IntentNavigator
import com.app.mybank.ui.usecase.IntentNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideIntentNavigator(): IntentNavigator = IntentNavigatorImpl()

    @Provides
    fun provideWeatherResource(
        @ApplicationContext context: Context
    ): AppResource =
        AppResourceImpl(context)

    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore = DataStoreImpl(
        context = context,
        file = CoreConstant.PREF_FILE
    )

    @Provides
    fun provideCurrencyUtil(): CurrencyUtil = CurrencyUtilImpl()

    @Provides
    fun provideDateUtil(): DateUtil = DateUtilImpl()
}