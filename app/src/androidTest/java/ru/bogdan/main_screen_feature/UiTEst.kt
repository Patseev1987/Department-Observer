package ru.bogdan.main_screen_feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import org.junit.Test
import ru.bogdan.core_ui.ui.theme.DepartmentObserverTheme
import ru.bogdan.login_feature.ui.LoginScreen
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreen

class UiTEst : BaseUiTest() {

    @Test
    fun testLoginScreen() = run {
        setComposeContent {
            LoginScreen({})
        }


        step("Open Login Screen") {
            onComposeScreen<LoginScreen>(composeTestRule) {
                icon {
                    assertIsDisplayed()
                }
                buttonSingIn {
                    assertIsDisplayed()
                }
                passwordField {
                    assertIsDisplayed()
                }
                loginField {
                    assertIsDisplayed()
                }
            }
        }



        step("Enter login and password") {
            onComposeScreen<LoginScreen>(composeTestRule) {
                passwordField {
                    this.performTextInput("123")
                }
                loginField {
                    this.performTextInput("ggg")
                }
            }
        }




        step("Press sign in") {
            onComposeScreen<LoginScreen>(composeTestRule) {
                buttonSingIn {
                    performClick()
                }
                composeTestRule.waitForIdle()
            }


        }
    }

    @Test
    fun testHomeScreens() = run {
        setComposeContent {
            DepartmentObserverTheme {
                HomeScreen(Modifier.fillMaxSize())
            }
        }

        step("Enter Home Screen") {
            onComposeScreen<HomeScreen>(composeTestRule) {
                userCard {
                    assertIsDisplayed()
                }
                paiChart {
                    assertIsDisplayed()
                }
                infoList {
                    assertIsDisplayed()
                }
                news {
                    assertIsNotDisplayed()
                }
                repairList {
                    assertIsNotDisplayed()
                }
            }
        }

        step("Press") {
            onComposeScreen<HomeScreen>(composeTestRule) {

                paiChart {
                    assertIsDisplayed()
                    performClick()
                }
                infoList {
                    assertIsDisplayed()
                    performClick()
                }
            }
        }
    }
}