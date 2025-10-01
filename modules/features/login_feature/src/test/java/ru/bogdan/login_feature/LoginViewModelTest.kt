package ru.bogdan.login_feature


import MainDispatcherRule
import data.NetworkRepository
import domain.user.LoginResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.bogdan.login_feature.ui.LoginIntent
import ru.bogdan.login_feature.ui.LoginScreenState
import ru.bogdan.login_feature.ui.LoginUiAction
import ru.bogdan.login_feature.ui.LoginViewModel
import utils.stubs.FakeDataStoreManager
import utils.stubs.FakeResourcesManager


@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private val networkRepository = mock<NetworkRepository>()
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        runBlocking {
            testDispatcher = UnconfinedTestDispatcher()
            viewModel = LoginViewModel(
                dispatcher = testDispatcher,
                networkRepository = networkRepository,
                dataStoreManager = FakeDataStoreManager,
                resourceManager = FakeResourcesManager
            )
            viewModel.handleIntent(LoginIntent.LogInPressed)
            whenever(networkRepository.login(anyString(), anyString())).thenReturn(
                Result.success(
                    LoginResponse(
                        "asd",
                        "asd",
                        "asd",
                        "asd"
                    )
                )
            )
        }
    }

    @Test
    fun `should get LoginResponse from networkRepository`() = runTest {
        val actual1 = viewModel.state.value.isLoading
        assertEquals(true, actual1)
        advanceTimeBy(1100)
        val actual2 = viewModel.state.value.isLoading
        assertEquals(false, actual2)
        advanceTimeBy(1500)
        val actual3 = viewModel.state.value.isLoading
        assertEquals(false, actual3)
        verify(networkRepository).login(anyString(), anyString())
    }

    @Test
    fun `should change uiAction`() = runTest {
        val expectedAction1 = LoginUiAction.ShowToast("Hello String with args asd")
        val expectedAction2 = LoginUiAction.GoToMainScreen
        advanceTimeBy(1100)
        assertEquals(expectedAction1, viewModel.uiAction.first())
        advanceTimeBy(1500)
        assertEquals(expectedAction2, viewModel.uiAction.first())
    }

    @Test
    fun `should change login`() = runTest {
        val expected = LoginScreenState().copy(login = "John")
        viewModel.handleIntent(LoginIntent.ChangeLogin("John"))
        val actual = viewModel.state.value.login
        assertEquals(expected.login, actual)
    }

    @Test
    fun `should change password`() = runTest {
        val expected = LoginScreenState().copy(password = "password")
        viewModel.handleIntent(LoginIntent.ChangePassword("password"))
        val actual = viewModel.state.value.password
        assertEquals(expected.password, actual)
    }
}