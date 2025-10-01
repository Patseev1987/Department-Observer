package ru.bogdan.main_screen_feature


import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.rule.KakaoComposeTestRule
import org.junit.Rule
import org.junit.runner.RunWith
import ru.bogdan.departmentobserver.MainActivity

@RunWith(AndroidJUnit4::class)
abstract class BaseUiTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    protected val compose = KakaoComposeTestRule(composeTestRule)

    protected fun setComposeContent(content: @Composable () -> Unit) {
        composeTestRule.activity.setContent{
            content()
        }
    }

}