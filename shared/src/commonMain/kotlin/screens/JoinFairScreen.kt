package screens

import FairDashColors
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class JoinFairScreen : Screen {
    override val key = uniqueScreenKey
    class JoinFairScreenModel : ScreenModel {
        var searchQuery by mutableStateOf("")
        val fairs = listOf("Fair 1", "Fair 2", "Fair 3", "Fair 4")
        var expanded by mutableStateOf(false)
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val model = navigator.rememberNavigatorScreenModel { JoinFairScreenModel() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Join a Fair",
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = FairDashColors.PRIMARY,
                                unfocusedBorderColor = FairDashColors.PRIMARY,
                                cursorColor = Color.Black,
                                textColor = Color.Black,
                                placeholderColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                            ),
                            value = model.searchQuery,
                            onValueChange = {
                                model.searchQuery = it
                                model.expanded = it.isNotEmpty()
                            },
                            label = { Text("Search Fair") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )
                        DropdownMenu(
                            expanded = model.expanded,
                            onDismissRequest = { model.expanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val searchResults = model.fairs.filter { it.contains(model.searchQuery, ignoreCase = true) }
                            searchResults.forEach { fair ->
                                DropdownMenuItem(onClick = {
                                    model.searchQuery = fair
                                    model.expanded = false
                                    /* Handle join fair action here */
                                }) {
                                    Text(text = fair, color = Color.Black)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}