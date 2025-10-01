package ru.bogdan.core_impl

import domain.mechanic.Machine
import domain.user.LoginResponse
import domain.user.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.bogdan.core_impl.data.network.NetWorkClientApplication
import ru.bogdan.core_impl.data.network.NetworkRepositoryImpl
import ru.bogdan.core_impl.data.network.UnauthorizedException
import ru.bogdan.core_impl.data.network.mapers.MapperWeb
import ru.bogdan.core_impl.data.network.models.mechanic.MachineWeb
import ru.bogdan.core_impl.data.network.models.user.LoginResponseWeb
import ru.bogdan.core_impl.data.network.models.user.UserWeb


class NetworkRepositoryImplTest {

    private val client = mock<NetWorkClientApplication>()

    private val mapper = MapperWeb()

    private val repository = NetworkRepositoryImpl(client, mapper)

    @Test
    fun loginTest() = runTest {
        //given
        whenever(client.login(anyString(), anyString())).thenReturn(LoginResponseWeb.NONE)
        val expected = LoginResponse.NONE
        //when
        val actual = repository.login("asd", "123").getOrNull()
        //then
        assertEquals(expected, actual)
        verify(client).login(anyString(), anyString())
    }


    @Test
    fun getUserByIdTestPositive() = runTest {
        //given
        whenever(client.getUserById(anyString())).thenReturn(UserWeb.NONE)
        val expected = User.NONE
        //when
        val actual = repository.getUserById("asd").getOrNull()
        //then
        assertEquals(expected, actual)
        verify(client).getUserById(anyString())
    }

    @Test
    fun getUserByIdTestNegative() = runTest {
        //given
        whenever(client.getUserById("123")).thenThrow(UnauthorizedException())
        val expected = "Unauthorized"
        //when
        try {
            repository.getUserById("123").getOrNull()
        } catch (e: UnauthorizedException) {
            //then
            assertEquals(expected, e.message)
            verify(client).getUserById(anyString())
        }
    }

    @Test
    fun getMachinesTest() = runTest {
        whenever(client.getMachines()).thenReturn(RepositoryDataFactory.getMachinesWeb())
        val expected = RepositoryDataFactory.getMachines()

        val actual = repository.getMachines().getOrNull()

        assertEquals(expected, actual)
    }

    @Test
    fun getMachineByIdTest() = runTest {
        whenever(client.getMachineById(anyString())).thenReturn(MachineWeb.NONE)
        val expected = Machine.NONE

        val actual = repository.getMachineById("m1").getOrNull()

        assertEquals(expected, actual)
    }

    @Test
    fun downloadDocById() = runTest {
        whenever(client.getDocById(anyString())).thenReturn(RepositoryDataFactory.byteArray)
        val expected = RepositoryDataFactory.byteArray

        val actual = repository.downloadDocById("m1").getOrNull()

        assertEquals(expected, actual)
    }

    @Test
    fun getInfoTest() = runTest {
        whenever(client.getInfo()).thenReturn(RepositoryDataFactory.getInfoWeb())
        val expected = RepositoryDataFactory.getInfo()

        val actual = repository.getInfo().getOrNull()

        assertEquals(expected, actual)
    }

}