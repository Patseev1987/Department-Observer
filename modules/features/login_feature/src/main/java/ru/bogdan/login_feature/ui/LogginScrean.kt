package ru.bogdan.login_feature.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import kotlinx.coroutines.launch
import navigation.NavigationEvent
import ru.bogdan.login_feature.R
import ru.bogdan.login_feature.util.getLoginComponent
import ui.common.AppOutlinedButton
import ui.theme.Emerald
import ui.theme.LocalSpacing
import ui.theme.MainGradient


@Composable
fun LoginScreen(
    onNavigateEvent: (NavigationEvent.Main) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val loginComponent = getLoginComponent()
    val viewModel: LoginViewModel = viewModel(factory = loginComponent.getViewModelFactory())
    val state = viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiAction.collect { action ->
            when (action) {
                is LoginUiAction.GoToMainScreen -> {
                    onNavigateEvent(NavigationEvent.Main(action.userId))
                }
                is LoginUiAction.ShowToast -> {
                    scope.launch {
                        Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.werehouse),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = MainGradient
                )
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .padding(spacing.medium),
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f), contentAlignment = Alignment.Center
            ) {

                Image(

                    painter = painterResource(R.drawable.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(15))
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(horizontal = spacing.medium),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                LoginTextField(
                    value = state.value.login,
                    onValueChange = {
                        viewModel.handleIntent(LoginIntent.ChangeLogin(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(R.string.login),
                    boarderColor = Emerald,
                )

                PasswordTextField(
                    value = state.value.password,
                    onValueChange = {
                        viewModel.handleIntent(LoginIntent.ChangePassword(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(R.string.password),
                    boarderColor = Emerald,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AppOutlinedButton(
                    onClick = {
                            viewModel.handleIntent(LoginIntent.LogInPressed)
                    }, color = Emerald
                ) {
                    Text("Sing in", color = Emerald, fontSize = 35.sp)
                }
            }

        }
        if(state.value.isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
            )
        }

    }
}

@Composable
fun PasswordTextField(
    value: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    fontSize: TextUnit = 25.sp,
    fontFamily: FontFamily = SansSerif,
    iconSize: Dp = 32.dp,
    boarderColor: Color = Black,
    isShowPassword: Boolean = false,
) {
    val spacing = LocalSpacing.current
    var isFocused by rememberSaveable { mutableStateOf(false) }
    var isShowPasswordTextField by rememberSaveable { mutableStateOf(isShowPassword) }
    val mainColor = remember { if (isError) Color.Red else boarderColor }

    val tempHint by mutableStateOf(if (isFocused || value.isNotBlank()) null else hint)

    Row(
        modifier = modifier
            .border(2.dp, color = mainColor, shape = RoundedCornerShape(15))
            .padding(spacing.medium),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(0.15f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                tint = mainColor,
                painter = painterResource(R.drawable.outline_lock_24),
                contentDescription = null,
            )
        }
        Box(modifier = Modifier.weight(0.7f)) {
            tempHint?.let {
                Text(
                    modifier = Modifier.padding(start = spacing.small),
                    text = hint ?: "",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
            BasicTextField(
                modifier = Modifier.onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        defaultKeyboardAction(imeAction = ImeAction.Send)
                    }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                ),
                visualTransformation = if (isShowPasswordTextField) VisualTransformation.None else PasswordVisualTransformation(),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = fontSize,
                    fontFamily = fontFamily,
                    color = mainColor,
                ),
            )
        }
        Box(
            modifier = Modifier.weight(0.15f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(spacing.large)
                    .clickable {
                        isShowPasswordTextField = !isShowPasswordTextField
                    },
                tint = mainColor,
                painter = painterResource(if (isShowPasswordTextField) R.drawable.outline_visibility_24 else R.drawable.outline_visibility_off_24),
                contentDescription = null,
            )
        }
    }
}


@Composable
fun LoginTextField(
    value: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    fontSize: TextUnit = 25.sp,
    fontFamily: FontFamily = SansSerif,
    iconSize: Dp = 32.dp,
    boarderColor: Color = Black,
) {
    val spacing = LocalSpacing.current
    var isFocused by rememberSaveable { mutableStateOf(false) }
    val mainColor = remember { if (isError) Color.Red else boarderColor }

    val tempHint by mutableStateOf(if (isFocused || value.isNotBlank()) null else hint)

    Row(
        modifier = modifier
            .border(2.dp, color = mainColor, shape = RoundedCornerShape(15))
            .padding(spacing.medium),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(0.15f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                tint = mainColor,
                painter = painterResource(R.drawable.outline_domino_mask_24),
                contentDescription = null,
            )
        }
        Box(modifier = Modifier.weight(0.7f)) {
            tempHint?.let {
                Text(
                    modifier = Modifier.padding(start = spacing.small),
                    text = hint ?: "",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }

            BasicTextField(
                modifier = Modifier.onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        defaultKeyboardAction(imeAction = ImeAction.Next)
                    }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = fontSize,
                    fontFamily = fontFamily,
                    color = if (value.isNotBlank() || isFocused) mainColor else Color.LightGray
                ),
            )
        }
        Box(
            modifier = Modifier
                .weight(0.15f)
                .size(iconSize),
            contentAlignment = Alignment.Center,
        ) {

        }
    }
}