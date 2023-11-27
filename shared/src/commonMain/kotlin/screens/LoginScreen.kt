import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import composables.Alert
import composables.AlertType
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lib.isEmailInvalid
import screens.SignupScreen


object LoginScreen : Screen {
    override val key = uniqueScreenKey

    @Composable
    override fun Content() {
        var alertType by remember { mutableStateOf(AlertType.WARNING) }
        var alertText by remember { mutableStateOf("") }
        var alertIsActive by remember { mutableStateOf(false) }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isEmailInvalid by remember { mutableStateOf(false) }
        var isPasswordInvalid by remember { mutableStateOf(false) }

        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
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


        if (alertIsActive) {
            LaunchedEffect(alertIsActive) {
                delay(3000)
                alertIsActive = false
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
                AnimatedVisibility(alertIsActive) {
                    Alert(alertType, alertText)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .wrapContentSize(Alignment.Center)
                ) {
                    Card(
                        shape = RoundedCornerShape(percent = 50),
                        backgroundColor = FairDashColors.PRIMARY,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "FairDash",
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.padding(18.dp))
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
                                AnimatedVisibility(isEmailInvalid) {
                                    Text(
                                        text = if (email.isEmpty()) "Email Required" else "Email must be valid",
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier
                                            .padding(bottom = 8.dp),
                                        color = Color.Red,
                                    )
                                }
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = {
                                        isEmailInvalid = isEmailInvalid(it)
                                        email = it
                                    },
                                    isError = isEmailInvalid,
                                    label = { Text("Email") },
                                    placeholder = { Text("email@provider.com") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    modifier = outlinedTextFieldModifiers,
                                    colors = outlinedTextFieldColors,
                                )
                            }
                            AnimatedVisibility(email.isNotEmpty() || password.isNotEmpty()) {
                                Column {
                                    AnimatedVisibility(isPasswordInvalid) {
                                        Text(
                                            text = if (password.isEmpty()) "Password must not be empty" else "Password must not contain spaces",
                                            style = MaterialTheme.typography.body1,
                                            modifier = Modifier
                                                .padding(bottom = 8.dp),
                                            color = Color.Red,
                                        )
                                    }
                                    OutlinedTextField(
                                        value = password,
                                        onValueChange = {
                                            isPasswordInvalid = it.isEmpty() || it.contains(" ")
                                            password = it
                                        },
                                        isError = isPasswordInvalid,
                                        label = { Text("Password") },
                                        placeholder = { Text("Password123") },
                                        singleLine = true,
                                        visualTransformation = PasswordVisualTransformation(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                        modifier = outlinedTextFieldModifiers,
                                        colors = outlinedTextFieldColors,
                                    )
                                }
                            }
                            Text(
                                text = "Forgot Password?",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .clickable {
                                        alertText = "Not Implemented Yet!"; alertType =
                                        AlertType.ERROR; alertIsActive = true
                                    },
                                color = Color.Black,
                                textDecoration = TextDecoration.Underline,
                            )
                            AnimatedVisibility((email.isNotEmpty() && password.isNotEmpty()) && (!isEmailInvalid && !isPasswordInvalid)) {
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        val client = HttpClient()
                                        var res: HttpResponse
                                        scope.launch {
                                            try {
                                                res = client.post("https://httpb")
                                                if (res.status.value != 200) {
                                                    alertText =
                                                        if (res.status.value == 401) "Invalid Credentials" else "Error ${res.status.value}: ${res.status.description}"
                                                    alertType = AlertType.ERROR
                                                    alertIsActive = true
                                                } else {
                                                    alertText = "Login Successful!"
                                                    alertType = AlertType.INFO
                                                    alertIsActive = true
                                                }

                                            } catch (e: Exception) {
                                                println("Error: $e")
                                                alertText = "Error: $e"
                                                alertType = AlertType.ERROR
                                                alertIsActive = true
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

