package com.app.mybank.core.usecase

import com.google.gson.Gson


inline fun <reified T : Any> String?.toObject(): T = Gson().fromJson(this, T::class.java)