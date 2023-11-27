package screens

import FairDashColors
import LoginScreen
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
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

    @Composable
    override fun Content() {
        var firstName by remember { mutableStateOf("") }
        var isFirstNameInvalid by remember { mutableStateOf(false) }
        var lastName by remember { mutableStateOf("") }
        var isLastNameInvalid by remember { mutableStateOf(false) }
        var email by remember { mutableStateOf("") }
        var isEmailInvalid by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf("") }
        var isPasswordInvalid by remember { mutableStateOf(false) }
        var confirmPassword by remember { mutableStateOf("") }
        var isConfirmPasswordInvalid by remember { mutableStateOf(false) }
        var phoneNumber by remember { mutableStateOf("") }
        var isPhoneNumberInvalid by remember { mutableStateOf(false) }
        var alertIsActive by remember { mutableStateOf(false) }
        var alertType by remember { mutableStateOf(AlertType.ERROR) }
        var alertText by remember { mutableStateOf("") }
        var stage by remember { mutableStateOf(0) }

        val navigator = LocalNavigator.currentOrThrow
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
                    shape = RoundedCornerShape(percent = 5),
                    modifier = Modifier.padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        AnimatedVisibility(stage > 0) {
                            Button(
                                onClick = {
                                    stage--
                                },
                            ) {
                                Text(
                                    text = "Back",
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

                        AnimatedVisibility(stage == 0) {
                            Column {
                                AnimatedVisibility(isFirstNameInvalid) {
                                    Text(
                                        text = if (firstName.isEmpty())
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
                                    value = firstName,
                                    onValueChange = {
                                        isFirstNameInvalid = isNameInvalid(it)
                                        firstName = it
                                    },
                                    label = {
                                        Text("First Name")
                                    },
                                    placeholder = { Text("John") },
                                    isError = isFirstNameInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text
                                    ),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility(isLastNameInvalid) {
                                    Text(
                                        text = if (lastName.isEmpty())
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
                                    value = lastName,
                                    onValueChange = {
                                        isLastNameInvalid = isNameInvalid(it)
                                        lastName = it

                                    },
                                    label = {
                                        Text("Last Name")
                                    },
                                    placeholder = { Text("Doe") },
                                    isError = isLastNameInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text
                                    ),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility((firstName.isNotEmpty() && lastName.isNotEmpty()) && (!isFirstNameInvalid && !isLastNameInvalid)) {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            stage = 1
                                        }
                                    ) {
                                        Text(
                                            text = "Next",
                                        )
                                    }
                                }
                            }
                        }

                        AnimatedVisibility(stage == 1) {
                            Column {
                                AnimatedVisibility(isEmailInvalid) {
                                    Text(
                                        text = if (email.isEmpty())
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
                                    value = email,
                                    onValueChange = {
                                        isEmailInvalid = isEmailInvalid(it)
                                        email = it
                                    },
                                    label = {
                                        Text("Email")
                                    },
                                    placeholder = { Text("email@provider.com") },
                                    isError = isEmailInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email
                                    ),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )

                                AnimatedVisibility(isPhoneNumberInvalid) {
                                    Text(
                                        text = if (phoneNumber.isEmpty())
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
                                    value = phoneNumber,
                                    onValueChange = {
                                        isPhoneNumberInvalid =
                                            isPhoneNumberInvalid(it)
                                        phoneNumber = it
                                    },
                                    label = {
                                        Text("Phone Number")
                                    },
                                    placeholder = { Text("12345678901") },
                                    isError = isPhoneNumberInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility((email.isNotEmpty() && phoneNumber.isNotEmpty()) && (!isEmailInvalid && !isPhoneNumberInvalid)) {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            stage = 2
                                        }
                                    ) {
                                        Text(
                                            text = "Next",
                                        )
                                    }
                                }
                            }
                        }
                        AnimatedVisibility(stage == 2) {
                            Column {
                            AnimatedVisibility(isPasswordInvalid) {
                                Text(
                                    text = if (password.isEmpty())
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
                                    value = password,
                                    onValueChange = {
                                        isPasswordInvalid = isPasswordInvalid(it)
                                        password = it
                                    },
                                    label = {
                                        Text("Password")
                                    },
                                    isError = isPasswordInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password
                                    ),
                                    visualTransformation = PasswordVisualTransformation(),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility(isConfirmPasswordInvalid) {
                                    Text(
                                        text = if (confirmPassword.isEmpty())
                                            "Confirm Password Required"
                                        else if (confirmPassword != password)
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
                                    value = confirmPassword,
                                    onValueChange = {
                                        isConfirmPasswordInvalid =
                                            it != password
                                        confirmPassword = it
                                    },
                                    label = {
                                        Text("Confirm Password")
                                    },
                                    isError = isConfirmPasswordInvalid,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password
                                    ),
                                    visualTransformation = PasswordVisualTransformation(),
                                    colors = outlinedTextFieldColors,
                                    modifier = outlinedTextFieldModifiers
                                )
                                AnimatedVisibility((password.isNotEmpty() && confirmPassword.isNotEmpty()) && (!isPasswordInvalid && !isConfirmPasswordInvalid)) {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            alertText = "Signup Not Implemented"
                                            alertType = AlertType.ERROR
                                            alertIsActive = true
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