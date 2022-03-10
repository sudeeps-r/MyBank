package com.app.mybank.core.scope

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpLogger

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit //This will provide authenticated retrofit instance

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicRetrofit //Basic network retrofit instance, no auth support

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultHeader