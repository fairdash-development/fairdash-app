package screens

import FairDashColors
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import composables.Alert
import composables.AlertType
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lib.isEmailInvalid


object LoginScreen : Screen {
    override val key = uniqueScreenKey

    class LoginScreenModel : ScreenModel {
        var alertType by mutableStateOf(AlertType.WARNING)
        var alertText by mutableStateOf("")
        var alertIsActive by mutableStateOf(false)
        var email by mutableStateOf("")
        var password by mutableStateOf("")
        var isEmailInvalid by mutableStateOf(false)
        var isPasswordInvalid by mutableStateOf(false)
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val model = navigator.rememberNavigatorScreenModel { LoginScreenModel() }
        val client = HttpClient()

        val outlinedTextFieldModifiers = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
        val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = FairDashColors.PRIMARY,
            unfocusedBorderColor = FairDashColors.PRIMARY,
            cursorColor = Color.Black,
            textColor = Color.Black,
            placeholderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
        )

        if (model.alertIsActive) {
            LaunchedEffect(model.alertIsActive) {
                delay(3000)
                model.alertIsActive = false
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            ExtendedFloatingActionButton(
                text = { Text("Create Account") },
                icon = { Icon(Icons.Default.Add, contentDescription = "create account icon") },
                onClick = {
                    navigator.push(SignupScreen())
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )
            AnimatedVisibility(model.alertIsActive) {
                Alert(model.alertType, model.alertText)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .wrapContentSize(Alignment.Center)
            ) {
                KamelImage(
                    resource = asyncPainterResource("https://i.imgur.com/k44ygtD.png"),
                    contentDescription = "FairDash Logo",
                    modifier = Modifier
                        .size(275.dp)
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    onFailure = {
                        model.alertText = "Failed to load FairDash Logo"; model.alertType =
                        AlertType.ERROR; model.alertIsActive = true
                    },
                    onLoading = { progress -> CircularProgressIndicator(progress) },
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                )
                Card(
                    shape = RoundedCornerShape(percent = 5),
                    modifier = Modifier.padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.padding(bottom = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            color = Color.Black,

                            )
                        Column {
                            AnimatedVisibility(model.isEmailInvalid) {
                                Text(
                                    text = if (model.email.isEmpty()) "Email Required" else "Email must be valid",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier
                                        .padding(bottom = 8.dp),
                                    color = Color.Red,
                                )
                            }
                            OutlinedTextField(
                                value = model.email,
                                onValueChange = {
                                    model.isEmailInvalid = isEmailInvalid(it)
                                    model.email = it
                                },
                                isError = model.isEmailInvalid,
                                label = { Text("Email") },
                                placeholder = { Text("email@provider.com") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = outlinedTextFieldModifiers,
                                colors = outlinedTextFieldColors,
                            )
                            AnimatedVisibility(model.email.isEmpty()) {
                                Text(
                                    text = "Forgot Email?",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier
                                        .padding(bottom = 16.dp)
                                        .clickable {
                                            model.alertText =
                                                "Not Implemented Yet!"; model.alertType =
                                            AlertType.ERROR; model.alertIsActive = true
                                        },
                                    color = Color.Black,
                                    textDecoration = TextDecoration.Underline,
                                )
                            }
                        }
                        AnimatedVisibility(model.email.isNotEmpty() || model.password.isNotEmpty()) {
                            Column {
                                AnimatedVisibility(model.isPasswordInvalid) {
                                    Text(
                                        text = if (model.password.isEmpty()) "Password must not be empty" else "Password must not contain spaces",
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier
                                            .padding(bottom = 8.dp),
                                        color = Color.Red,
                                    )
                                }
                                OutlinedTextField(
                                    value = model.password,
                                    onValueChange = {
                                        model.isPasswordInvalid = it.isEmpty() || it.contains(" ")
                                        model.password = it
                                    },
                                    isError = model.isPasswordInvalid,
                                    label = { Text("Password") },
                                    placeholder = { Text("Password123") },
                                    singleLine = true,
                                    visualTransformation = PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    modifier = outlinedTextFieldModifiers,
                                    colors = outlinedTextFieldColors,
                                )
                                AnimatedVisibility(model.password.isEmpty()) {
                                    Text(
                                        text = "Forgot Password?",
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier
                                            .padding(bottom = 16.dp)
                                            .clickable {
                                                model.alertText =
                                                    "Not Implemented Yet!"; model.alertType =
                                                AlertType.ERROR; model.alertIsActive = true
                                            },
                                        color = Color.Black,
                                        textDecoration = TextDecoration.Underline,
                                    )
                                }
                            }
                        }
                        AnimatedVisibility((model.email.isNotEmpty() && model.password.isNotEmpty()) && (!model.isEmailInvalid && !model.isPasswordInvalid)) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    navigator.push(HomeScreen())
                                    model.screenModelScope.launch {
                                        try {
                                            val res = client.post("https://google.com")
                                            if (res.status.value != 200) {
                                                model.alertText =
                                                    if (res.status.value == 401) "Invalid Credentials" else "Error ${res.status.value}: ${res.status.description}"
                                                model.alertType = AlertType.ERROR
                                                model.alertIsActive = true
                                            } else {
                                                model.alertText = "Login Successful!"
                                                model.alertType = AlertType.INFO
                                                model.alertIsActive = true
                                            }
                                        } catch (e: Exception) {
                                            model.alertText = "$e"
                                            model.alertType = AlertType.ERROR
                                            model.alertIsActive = true
                                        } finally {
                                            client.close()
                                        }
                                    }
                                }
                            ) {
                                Text(
                                    text = "Login",
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}