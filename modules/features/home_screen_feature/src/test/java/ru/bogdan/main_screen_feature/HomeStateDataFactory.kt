package ru.bogdan.main_screen_feature

import domain.info.Info
import domain.mechanic.Machine
import domain.mechanic.MachineState
import domain.user.Role
import domain.user.User
import ru.bogdan.core_ui.ui.common.extansions.getColor
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenViewModel.Companion.WHOLE_CIRCLE
import ru.bogdan.main_screen_feature.ui.homeScreen.InfoAboutMachines

object HomeStateDataFactory {
    fun getInfo(): Result<List<Info>> = Result.success(
        listOf(
            Info("1", "a", "Hello"),
            Info("2", "b", "GoodBay"),
            Info("3", "b", "Welcome"),
        )
    )

    fun getUser(): Result<User> = Result.success(User.NONE.copy(name = "John", surname = "Smith", role = Role.MECHANIC))

    fun getMachines(): Result<List<Machine>> = Result.success(
        listOf(
            Machine.NONE
        )
    )

 fun getInfoAboutMachines(): List<InfoAboutMachines> {
     val machines = getMachines().getOrThrow()
     val tempInfo = mutableListOf<InfoAboutMachines>()
     val angle = WHOLE_CIRCLE / machines.size
     MachineState.entries.forEach { state ->
         val count = machines.count { it.state == state }
         tempInfo.add(
             InfoAboutMachines(
                 state = state,
                 percentage = count * angle,
                 title = "Working",
                 color = state.getColor(),
                 count = count
             )
         )
     }
     return tempInfo
 }
}