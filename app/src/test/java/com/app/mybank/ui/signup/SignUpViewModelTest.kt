package com.app.mybank.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.mybank.R
import com.app.mybank.core.usecase.Resource
import com.app.mybank.ui.signup.service.RegisterRequest
import com.app.mybank.ui.signup.service.SignUpRepo
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

class SignUpViewModelTest {

    private val repo: SignUpRepo = mock()
    private val appResource: AppResource = mock()
    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setUp() {
        viewModel = SignUpViewModel(
            repo,
            appResource
        )
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test signup invalid username`() = runBlocking {
        val userNameError = "Invalid User Name"
        whenever(
            appResource.getString(
                R.string.login_invalid_user_name
            )
        ).thenReturn(userNameError)
        viewModel.doRegister()
        viewModel.doSignUp.observeForever {
            Assert.assertEquals(userNameError, it.message)
            Assert.assertEquals(ViewConstant.VALIDATION_USER_NAME, it.code)
            Assert.assertEquals(Resource.Status.ERROR, it.status)
        }
    }

    @Test
    fun `test signup invalid password`() = runBlocking {
        val passwordError = "Invalid password"
        viewModel.userName.set("userName")
        whenever(
            appResource.getString(
                R.string.login_invalid_password
            )
        ).thenReturn(passwordError)
        viewModel.doRegister()
        viewModel.doSignUp.observeForever {
            Assert.assertEquals(passwordError, it.message)
            Assert.assertEquals(ViewConstant.VALIDATION_PASSWORD, it.code)
            Assert.assertEquals(Resource.Status.ERROR, it.status)
        }
    }

    @Test
    fun `test signup password mismatch`() = runBlocking {
        val passwordError = "Password mismatch"
        viewModel.userName.set("userName")
        viewModel.password.set("password")
        whenever(
            appResource.getString(
                R.string.signup_password_valid_error
            )
        ).thenReturn(passwordError)
        viewModel.doRegister()
        viewModel.doSignUp.observeForever {
            Assert.assertEquals(passwordError, it.message)
            Assert.assertEquals(ViewConstant.VALIDATION_PASSWORD, it.code)
            Assert.assertEquals(Resource.Status.ERROR, it.status)
        }
    }

    @Test
    fun `test signup`() = runBlocking {
        viewModel.userName.set("userName")
        viewModel.password.set("password")
        viewModel.confirmPassword.set("password")
        val request = RegisterRequest(
            userName = viewModel.userName.get().orEmpty(),
            password = viewModel.password.get().orEmpty()
        )
        val response = MockDataStore.mockLoginResponse
        whenever(repo.doRegister(request)).thenReturn(response)
        viewModel.doSignUp.observeForever {
            Assert.assertEquals(response, it.data)
            Assert.assertEquals(Resource.Status.SUCCESS, it.status)
        }
    }
}