package ru.bogdan.machine_list

import MainDispatcherRule
import data.NetworkRepository
import domain.mechanic.Machine
import domain.mechanic.MachineModel
import domain.mechanic.MachineState
import domain.mechanic.MachineType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.bogdan.machine_list.ui.MachineListIntent
import ru.bogdan.machine_list.ui.MachineListViewModel
import utils.stubs.FakeResourcesManager

@OptIn(ExperimentalCoroutinesApi::class)
class MachineListViewModelTest {

    private val networkRepository = mock<NetworkRepository>()
    lateinit var vm: MachineListViewModel
    lateinit var dispatcher: TestDispatcher
    private val machineList = listOf(
        Machine.NONE,
        Machine.NONE.copy(model = MachineModel.SMU50_30, type = MachineType.MILLING_CNC, yearOfManufacture = 2007),
        Machine.NONE.copy(state = MachineState.REPAIR, yearOfManufacture = 2007),
        Machine.NONE.copy(state = MachineState.REPAIR),
    )

    @get:Rule
    val mainRule = MainDispatcherRule()

    @Before
    fun setUp() {
        runTest {
            dispatcher = UnconfinedTestDispatcher()

            whenever(networkRepository.getMachines()).thenReturn(Result.success(machineList))

            vm = MachineListViewModel(
                networkRepository = networkRepository,
                dispatcher = dispatcher,
                resourceManager = FakeResourcesManager
            )
            advanceUntilIdle()
        }
    }

    @Test
    fun `get machine list`() = runTest {
        verify(networkRepository).getMachines()
    }

    @Test
    fun `apply MachineModel filters`() = runTest {
        vm.handleIntent(MachineListIntent.SetMachineModelFilter(MachineModel.T_16K20))
        advanceUntilIdle()
        vm.handleIntent(MachineListIntent.ApplyFilters)
        assertEquals(3, vm.state.value.machines.size)
    }

    @Test
    fun `apply MachineState filters`() = runTest {
        vm.handleIntent(MachineListIntent.SetMachineStateFilter(MachineState.REPAIR))
        advanceUntilIdle()
        vm.handleIntent(MachineListIntent.ApplyFilters)
        assertEquals(2, vm.state.value.machines.size)
    }

    @Test
    fun `apply MachineType filters`() = runTest {
        vm.handleIntent(MachineListIntent.SetMachineTypeFilter(MachineType.MILLING_CNC))
        advanceUntilIdle()
        vm.handleIntent(MachineListIntent.ApplyFilters)
        assertEquals(1, vm.state.value.machines.size)
    }

    @Test
    fun `apply Years filters`() = runTest {
        vm.handleIntent(MachineListIntent.SetMachineYearFilter(2000..2008))
        advanceUntilIdle()
        vm.handleIntent(MachineListIntent.ApplyFilters)
        assertEquals(2, vm.state.value.machines.size)
    }

    @Test
    fun `apply drop filters`() = runTest {
        vm.handleIntent(MachineListIntent.SetMachineStateFilter(MachineState.REPAIR))
        advanceUntilIdle()
        vm.handleIntent(MachineListIntent.ApplyFilters)
        assertEquals(2, vm.state.value.machines.size)
        vm.handleIntent(MachineListIntent.DropFilters)
        advanceUntilIdle()
        assertEquals(4, vm.state.value.machines.size)
    }

    @Test
    fun `should change filters state`() = runTest {
        vm.handleIntent(MachineListIntent.ChangeFilterShowState)
        advanceUntilIdle()
        assertEquals(true, vm.state.value.isFiltersShow)
        vm.handleIntent(MachineListIntent.ChangeFilterShowState)
        advanceUntilIdle()
        assertEquals(false, vm.state.value.isFiltersShow)
    }
}
