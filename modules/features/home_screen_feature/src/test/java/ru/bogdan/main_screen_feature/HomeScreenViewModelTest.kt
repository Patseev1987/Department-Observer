package ru.bogdan.main_screen_feature

import MainDispatcherRule
import data.NetworkRepository
import domain.mechanic.MachineState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenIntent
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenState
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenViewModel
import utils.stubs.FakeDataStoreManager
import utils.stubs.FakeResourcesManager

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {
    @get:Rule
    val mainRule = MainDispatcherRule()

    private val networkRepository = mock<NetworkRepository>()
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var viewModel: HomeScreenViewModel
    val data = HomeStateDataFactory

    @Before
    fun setup() {
        runTest {
            testDispatcher = UnconfinedTestDispatcher()
            whenever(networkRepository.getInfo()).thenReturn(data.getInfo())
            whenever(networkRepository.getUserById(any())).thenReturn(data.getUser())
            whenever(networkRepository.getMachines()).thenReturn(data.getMachines())
            viewModel = HomeScreenViewModel(
                dispatcher = testDispatcher,
                networkRepository = networkRepository,
                dataStoreManager = FakeDataStoreManager,
                manager = FakeResourcesManager
            )
        }
    }

    @Test
    fun `should prepare data`() = runTest {


        val user = data.getUser().getOrThrow()
        val machines = data.getMachines().getOrThrow()
        val tempInfo = data.getInfo().getOrThrow()

        val expected = HomeScreenState(
            name = user.name,
            surname = user.surname,
            patronymic = user.patronymic,
            photo = user.photoUrl,
            role = "Working",
            isLoading = false,
            infoAboutMachines = data.getInfoAboutMachines(),
            machines = machines,
            repairList = machines.filter { it.state == MachineState.REPAIR },
            info = tempInfo
        )

        viewModel = HomeScreenViewModel(
            dispatcher = testDispatcher,
            networkRepository = networkRepository,
            dataStoreManager = FakeDataStoreManager,
            manager = FakeResourcesManager
        )
        val actual = viewModel.state.value

        assertEquals(expected, actual)
    }

    @Test
    fun `should show repair list`() = runTest {
        viewModel.handleIntent(HomeScreenIntent.ShowRepairList(true))
        advanceUntilIdle()
        assertEquals(true, viewModel.state.value.isShowRepairList)
    }

    @Test
    fun `should show info`() = runTest {
        viewModel.handleIntent(HomeScreenIntent.ShowInfoList(true))
        advanceUntilIdle()
        assertEquals(true, viewModel.state.value.isShowInfoList)
    }
}