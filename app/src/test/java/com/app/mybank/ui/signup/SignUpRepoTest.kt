package com.app.mybank.ui.signup

import com.app.mybank.ui.signup.service.RegisterRequest
import com.app.mybank.ui.signup.service.SignUpApi
import com.app.mybank.ui.signup.service.SignUpRepo
import com.app.mybank.ui.signup.service.SignUpRepoImpl
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.util.MockDataStore
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SignUpRepoTest {

    private val appResource: AppResource = mock()
    private val api: SignUpApi = mock()
    private lateinit var repo: SignUpRepo

    @Before
    fun setUp() {
        repo = SignUpRepoImpl(
            api = api,
            appResource = appResource
        )
    }

    @Test
    fun `test signup`() = runBlocking {
        val request = RegisterRequest(
            userName = "userName",
            password = "password"
        )
        val response = Response.success(MockDataStore.loginResponse)
        whenever(api.doRegister(request)).thenReturn(response)
        val result = repo.doRegister(request)
        Assert.assertEquals(MockDataStore.loginResponse, result.data)
    }
}