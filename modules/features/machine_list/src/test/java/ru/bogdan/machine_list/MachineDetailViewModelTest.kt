package ru.bogdan.machine_list

import MainDispatcherRule
import data.NetworkRepository
import domain.mechanic.Machine
import domain.mechanic.MachineDocument
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.bogdan.core_ui.ui.common.extansions.getColor
import ru.bogdan.machine_list.ui.machineDetails.MachineDetailState
import ru.bogdan.machine_list.ui.machineDetails.MachineDetailsIntent
import ru.bogdan.machine_list.ui.machineDetails.MachineDetailsViewModel
import utils.stubs.FakeFileManager
import utils.stubs.FakeResourcesManager

@OptIn(ExperimentalCoroutinesApi::class)
class MachineDetailViewModelTest {

    lateinit var networkRepository: NetworkRepository
    lateinit var vm: MachineDetailsViewModel
    lateinit var dispatcher: TestDispatcher
    private val it = Machine.NONE
    private val machineState = MachineDetailState(
        isLoading = false,
        name = it.name,
        model = it.model.name,
        imageUrl = it.imageUrl,
        description = it.description,
        state = "Hello String",
        type = "Hello String",
        yearOfManufacture = it.yearOfManufacture,
        parts = it.parts,
        docs = it.docs,
        oils = it.oils,
        color = it.state.getColor(),
        states = listOf(
            "Hello String",
            "Hello String",
            "Hello String"
        ),
    )

    @get:Rule
    val mainRule = MainDispatcherRule()

    @Before
    fun setUp() {
        runTest {
            dispatcher = UnconfinedTestDispatcher()
            networkRepository = mock()
            whenever(networkRepository.getMachineById(any())).thenReturn(
                Result.success(Machine.NONE)
            )
            whenever(networkRepository.downloadDocById(any())).thenReturn(
                Result.success("123".toByteArray())
            )
            vm = MachineDetailsViewModel(
                dispatcher = dispatcher,
                networkRepository = networkRepository,
                machineId = "machine1",
                resourceManager = FakeResourcesManager,
                fileManager = FakeFileManager
            )
        }
    }

    @Test
    fun `get machine list`() = runTest {
        advanceUntilIdle()
        assertEquals(machineState, vm.state.value)
        verify(networkRepository).getMachineById(any())
    }

    @Test
    fun `should save doc`() = runTest {
        vm.handleIntent(MachineDetailsIntent.ShowDialog(MachineDocument.NONE.copy(name = "document")))
        advanceUntilIdle()
        vm.handleIntent(MachineDetailsIntent.SaveDoc)
        advanceUntilIdle()
        verify(networkRepository).downloadDocById(any())
    }

    @Test
    fun `should change expand`() = runTest {
        vm.handleIntent(MachineDetailsIntent.ExpandChange(true))
        advanceUntilIdle()
        assertEquals(true, vm.state.value.expanded)
    }

    @Test
    fun `should show dialog`() = runTest {
        vm.handleIntent(MachineDetailsIntent.ShowDialog(MachineDocument.NONE))
        advanceUntilIdle()
        assertEquals(true, vm.state.value.isShowDialog)
    }

    @Test
    fun `should hide dialog`() = runTest {
        vm.handleIntent(MachineDetailsIntent.ShowDialog(MachineDocument.NONE))
        advanceUntilIdle()
        assertEquals(true, vm.state.value.isShowDialog)
        vm.handleIntent(MachineDetailsIntent.HideDialog)
        advanceUntilIdle()
        assertEquals(false, vm.state.value.isShowDialog)
    }

    @Test
    fun `should select state`() = runTest {
        val expect = "Working"
        vm.handleIntent(MachineDetailsIntent.SelectState(expect))
        advanceUntilIdle()
        assertEquals(expect, vm.state.value.state)
    }
}