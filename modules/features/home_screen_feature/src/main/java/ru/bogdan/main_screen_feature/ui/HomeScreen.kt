package ru.bogdan.main_screen_feature.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import ru.bogdan.main_screen_feature.R
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenIntent
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenState
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenUiAction
import ru.bogdan.main_screen_feature.ui.homeScreen.HomeScreenViewModel
import ru.bogdan.main_screen_feature.ui.homeScreen.userCard.UserCard
import ru.bogdan.main_screen_feature.utils.getHomeScreenComponent
import ui.theme.Emerald
import ui.theme.LocalSpacing
import ui.theme.MainGradient
import ui.theme.Typography

@Composable
fun MainScreen(
    userId: String,
    modifier: Modifier = Modifier
) {
    Log.i("MainScreen", "Main Screen")
    val components = getHomeScreenComponent(userId)
    val viewModel: HomeScreenViewModel = viewModel(factory = components.getViewModelFactory())
    val state = viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = { },
        bottomBar = {
        }
    ) { paddingValues ->
        val context = LocalContext.current

        LaunchedEffect(true) {

            viewModel.uiAction.collect { action ->
                when (action) {
                    is HomeScreenUiAction.GoToNextScreen -> TODO()
                    HomeScreenUiAction.Logout -> TODO()
                    is HomeScreenUiAction.ShowToast -> {
                        Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.user_card),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            UserCard(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = MainGradient
                    )
                    .padding(paddingValues),
                imageSize = 200.dp,
                strokeWidth = 15.dp,
                colorNearPhoto = Emerald,
                photoContent = {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = state.value.photo ?: "",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                clipToBounds = true,
            )
                },
                nameContent = {
                    UserName(state)
                },

             dataContent = {
                LazyColumn(
                ) {
                    repeat(10) {
                        item {
                            Box(
                                modifier = Modifier
                                    .size(800.dp)
                                    .background(Color.Red)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
            )
        }
    }
}



@Composable
fun MainScreenBottomNavigationBar(
    navigationItems: List<ObserverNavigationItem>,
    onClick: (ObserverNavigationItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        navigationItems.forEach { navigationItem ->
            NavigationBarItem(
                selected = true,
                onClick = {
                    onClick.invoke(navigationItem)
                },
                icon = {
                    Icon(
                        painter = painterResource(navigationItem.drawableId),
                        contentDescription = navigationItem.title,
                    )
                }
            )
        }
    }
}

@Composable
fun UserName(
    state: State<HomeScreenState>
){
    val state by state
    val spacing = LocalSpacing.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = state.name,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontStyle = Typography.headlineMedium.fontStyle,
                fontSize = Typography.headlineMedium.fontSize,
                color = Emerald,
            )
            Text(
                text = state.surname,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = spacing.medium),
                fontStyle = Typography.headlineMedium.fontStyle,
                fontSize = Typography.headlineMedium.fontSize,
                color = Emerald,
            )
        }

        Text(
            text = state.patronymic,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = spacing.small),
            fontStyle = Typography.headlineMedium.fontStyle,
            fontSize = Typography.headlineMedium.fontSize,
            color = Emerald,
        )

        Text(
            text = state.role.name,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = spacing.small),
            color = Emerald,
            fontStyle = Typography.displaySmall.fontStyle,
            fontSize = Typography.displaySmall.fontSize,
            fontWeight = FontWeight.Bold,
        )
    }
}