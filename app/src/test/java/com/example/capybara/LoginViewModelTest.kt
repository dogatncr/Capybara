package com.example.capybara

import android.content.Context
import android.provider.ContactsContract.Data
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.capybara.data.util.DataStoreManager
import com.example.capybara.domain.usecases.LoginUseCase
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataStoreManager:DataStoreManager
    private lateinit var loginUseCase : LoginUseCase
    val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initSetUp(){

        Dispatchers.setMain(testDispatcher)
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns mockk(relaxed = true)
        val mContextMock = mockk<Context>(relaxed = true)
        val auth= FirebaseAuth.getInstance()
        loginUseCase=LoginUseCase(auth)
        //loginUseCase = Mockito.mock(loginUseCase::class.java)
        dataStoreManager =DataStoreManager(mContextMock)
        loginViewModel = LoginViewModel(loginUseCase,dataStoreManager)

    }
    @Test
    fun onResponseReceived_checkSuccessState_isSuccess(){
        loginViewModel.onLoginClick("dogatuncer@gmail.com","123456")
        runTest {
            val collectJob = launch {
                loginViewModel.uiState.collect {
                    when (it) {
                        LoginUiState.Success -> {
                            Truth.assertThat(loginViewModel.uiState).isEqualTo(LoginUiState.Success)

                        }
                    }
                }
            }
            collectJob.cancel()
        }
    }
}