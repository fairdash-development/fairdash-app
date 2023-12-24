package screens

import FairDashColors
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import composables.Alert
import composables.AlertType
import lib.isEmailInvalid
import lib.isNameInvalid
import lib.isPasswordInvalid
import lib.isPhoneNumberInvalid

class SignupScreen : Screen {
    override val key = uniqueScreenKey
    class SignupScreenModel : ScreenModel {
        var firstName by mutableStateOf("")
        var isFirstNameInvalid by mutableStateOf(false)
        var lastName by mutableStateOf("")
        var isLastNameInvalid by mutableStateOf(false)
        var email by mutableStateOf("")
        var isEmailInvalid by mutableStateOf(false)
        var password by mutableStateOf("")
        var isPasswordInvalid by mutableStateOf(false)
        var confirmPassword by mutableStateOf("")
        var isConfirmPasswordInvalid by mutableStateOf(false)
        var phoneNumber by mutableStateOf("")
        var isPhoneNumberInvalid by mutableStateOf(false)
        var alertIsActive by mutableStateOf(false)
        var alertType by mutableStateOf(AlertType.ERROR)
        var alertText by mutableStateOf("")
        var stage by mutableIntStateOf(0)
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val model = navigator.rememberNavigatorScreenModel { SignupScreenModel() }
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

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ExtendedFloatingActionButton(
                text = { Text("Login") },
                onClick = {
                    navigator.push(LoginScreen)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
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
                Card(
                    shape = RoundedCornerShape(percent = 5),
                    modifier = Modifier.padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        AnimatedVisibility(model.stage > 0) {
                            Button(
                                onClick = {
                                    model.stage--
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "back",
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Text(
                            text = "Signup for FairDash",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(bottom = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            color = Color.Black,

                            )

                        AnimatedVisibility(model.stage == 0) {
                            Column {
                                AnimatedVisibility(model.isFirstNameInvalid) {
                                    Text(
                                        text = if (model.firstName.isEmpty())
                                            "First name Required"
                                        else
                                            "First name must be valid",
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier
                                            .padding(bottom = 8.dp),
                                        color = Color.Red,
                                    )
                                }
                                OutlinedTextField(
                                    value = model.firstName,
                                    onValueChange = {
                                        model.isFirstNameInvalid = isNameInvalid(it)
                                        model.firstName = it
                                    },
                                    label = {
                                        Text("First Name")
                                    },
                                    placeholder = { Text("John") },
                                    isError = model.isFirstNameInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text
                                    ),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility(model.isLastNameInvalid) {
                                    Text(
                                        text = if (model.lastName.isEmpty())
                                            "Last name Required"
                                        else
                                            "Last name must be valid",
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier
                                            .padding(bottom = 8.dp),
                                        color = Color.Red,
                                    )
                                }
                                OutlinedTextField(
                                    value = model.lastName,
                                    onValueChange = {
                                        model.isLastNameInvalid = isNameInvalid(it)
                                        model.lastName = it

                                    },
                                    label = {
                                        Text("Last Name")
                                    },
                                    placeholder = { Text("Doe") },
                                    isError = model.isLastNameInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text
                                    ),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility((model.firstName.isNotEmpty() && model.lastName.isNotEmpty()) && (!model.isFirstNameInvalid && !model.isLastNameInvalid)) {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            model.stage = 1
                                        }
                                    ) {
                                        Text(
                                            text = "Next",
                                        )
                                    }
                                }
                            }
                        }

                        AnimatedVisibility(model.stage == 1) {
                            Column {
                                AnimatedVisibility(model.isEmailInvalid) {
                                    Text(
                                        text = if (model.email.isEmpty())
                                            "Email Required"
                                        else
                                            "Email must be valid",
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
                                    label = {
                                        Text("Email")
                                    },
                                    placeholder = { Text("email@provider.com") },
                                    isError = model.isEmailInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email
                                    ),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )

                                AnimatedVisibility(model.isPhoneNumberInvalid) {
                                    Text(
                                        text = if (model.phoneNumber.isEmpty())
                                            "Phone Number Required"
                                        else
                                            "Phone Number must be valid",
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier
                                            .padding(bottom = 8.dp),
                                        color = Color.Red,
                                    )
                                }
                                OutlinedTextField(
                                    value = model.phoneNumber,
                                    onValueChange = {
                                        model.isPhoneNumberInvalid =
                                            isPhoneNumberInvalid(it)
                                        model.phoneNumber = it
                                    },
                                    label = {
                                        Text("Phone Number")
                                    },
                                    placeholder = { Text("12345678901") },
                                    isError = model.isPhoneNumberInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility((model.email.isNotEmpty() && model.phoneNumber.isNotEmpty()) && (!model.isEmailInvalid && !model.isPhoneNumberInvalid)) {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            model.stage = 2
                                        }
                                    ) {
                                        Text(
                                            text = "Next",
                                        )
                                    }
                                }
                            }
                        }
                        AnimatedVisibility(model.stage == 2) {
                            Column {
                            AnimatedVisibility(model.isPasswordInvalid) {
                                Text(
                                    text = if (model.password.isEmpty())
                                        "Password Required"
                                    else
                                        "Password can not contain spaces",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier
                                        .padding(bottom = 8.dp),
                                    color = Color.Red,
                                )
                            }
                                OutlinedTextField(
                                    value = model.password,
                                    onValueChange = {
                                        model.isPasswordInvalid = isPasswordInvalid(it)
                                        model.password = it
                                    },
                                    label = {
                                        Text("Password")
                                    },
                                    isError = model.isPasswordInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password
                                    ),
                                    visualTransformation = PasswordVisualTransformation(),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility(model.isConfirmPasswordInvalid) {
                                    Text(
                                        text = if (model.confirmPassword.isEmpty())
                                            "Confirm Password Required"
                                        else if (model.confirmPassword != model.password)
                                            "Passwords do not match"
                                        else
                                            "Password can not contain spaces",
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier
                                            .padding(bottom = 8.dp),
                                        color = Color.Red,
                                    )
                                }
                                OutlinedTextField(
                                    value = model.confirmPassword,
                                    onValueChange = {
                                        model.isConfirmPasswordInvalid =
                                            it != model.password
                                        model.confirmPassword = it
                                    },
                                    label = {
                                        Text("Confirm Password")
                                    },
                                    isError = model.isConfirmPasswordInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password
                                    ),
                                    visualTransformation = PasswordVisualTransformation(),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility((model.password.isNotEmpty() && model.confirmPassword.isNotEmpty()) && (!model.isPasswordInvalid && !model.isConfirmPasswordInvalid)) {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            model.alertText = "Signup Not Implemented"
                                            model.alertType = AlertType.ERROR
                                            model.alertIsActive = true
                                        }
                                    ) {
                                        Text(
                                            text = "Signup",
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}