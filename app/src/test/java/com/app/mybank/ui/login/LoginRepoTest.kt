package com.app.mybank.ui.login

import com.app.mybank.core.dao.DataStore
import com.app.mybank.core.util.PreferenceKey
import com.app.mybank.ui.login.service.LoginApi
import com.app.mybank.ui.login.service.LoginRepo
import com.app.mybank.ui.login.service.LoginRepoImpl
import com.app.mybank.ui.login.service.LoginRequest
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.util.MockDataStore
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class LoginRepoTest {

    private val appResource: AppResource = mock()
    private val api: LoginApi = mock()
    private val dataStore: DataStore = mock()
    private lateinit var repo: LoginRepo

    @Before
    fun setUp() {
        repo = LoginRepoImpl(
            appResource = appResource,
            api = api,
            dataStore = dataStore
        )
    }

    @Test
    fun `test login`() = runBlocking {
        val request = LoginRequest(
            userName = "username",
            password = "password"
        )
        val response = Response.success(MockDataStore.loginResponse)
        whenever(api.doLogin(request)).thenReturn(response)
        val result = repo.doLogin(request)
        Assert.assertEquals(MockDataStore.loginResponse, result.data)
        verify(dataStore).setValue(
            PreferenceKey.TOKEN,
            MockDataStore.loginResponse.token
        )
    }
}