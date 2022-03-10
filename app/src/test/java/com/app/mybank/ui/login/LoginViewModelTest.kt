package com.app.mybank.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.login.service.LoginRepo
import com.app.mybank.ui.login.service.LoginRequest
import com.app.mybank.ui.usecase.AppResource
import com.app.mybank.ui.util.ViewConstant
import com.app.mybank.util.MockDataStore
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    private val repo: LoginRepo = mock()
    private val appResource: AppResource = mock()
    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(
            repo = repo,
            appResource = appResource
        )
    }

    @Test
    fun `test login user name verification`() = runBlocking {
        val userNameError = "Enter user name"
        whenever(
            appResource.getString(R.string.login_invalid_user_name)
        ).thenReturn(userNameError)
        viewModel.performLogin()
        viewModel.doLogin.observeForever {
            Assert.assertEquals(userNameError, it.message)
            Assert.assertEquals(ViewConstant.VALIDATION_USER_NAME, it.code)
            Assert.assertEquals(Resource.Status.ERROR, it.status)
        }
    }

    @Test
    fun `test login user password verification`() = runBlocking {
        val passwordError = "Enter password"
        whenever(
            appResource.getString(R.string.login_invalid_password)
        ).thenReturn(passwordError)
        viewModel.userName.set("UserName")
        viewModel.performLogin()
        viewModel.doLogin.observeForever {
            Assert.assertEquals(passwordError, it.message)
            Assert.assertEquals(ViewConstant.VALIDATION_PASSWORD, it.code)
            Assert.assertEquals(Resource.Status.ERROR, it.status)
        }
    }

    @Test
    fun `test login `() = runBlocking {
        viewModel.userName.set("UserName")
        viewModel.password.set("Password")
        val request = LoginRequest(
            userName = viewModel.userName.get().orEmpty(),
            password = viewModel.password.get().orEmpty()
        )
        val response = MockDataStore.mockLoginResponse
        whenever(repo.doLogin(request)).thenReturn(response)
        viewModel.performLogin()
        viewModel.doLogin.observeForever {
            Assert.assertEquals(response, it.data)
            Assert.assertEquals(Resource.Status.SUCCESS, it.status)
        }
    }
}