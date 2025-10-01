package ru.bogdan.main_screen_feature

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode


class LoginScreen(semanticsProvider: SemanticsNodeInteractionsProvider) : ComposeScreen<LoginScreen>(
    semanticsProvider = semanticsProvider,
) {
    val icon: KNode = child {
        hasTestTag("icon")
    }

    val passwordField: KNode = child {
        hasTestTag("password_field")
    }

    val loginField: KNode = child {
        hasTestTag("login_field")
    }

    val buttonSingIn: KNode = child {
        hasTestTag("button_sing_in")
    }
}

class HomeScreen(semanticsProvider: SemanticsNodeInteractionsProvider) : ComposeScreen<HomeScreen>(
    semanticsProvider = semanticsProvider,
) {
    val userCard: KNode = child {
        hasTestTag("user_card")
    }

    val paiChart: KNode = child {
        hasTestTag("pai_chart")
    }

    val infoList: KNode = child {
        hasTestTag("info_list")
    }

    val repairList: KNode = child {
        hasTestTag("repair_list")
    }

    val news: KNode = child {
        hasTestTag("news")
    }
}