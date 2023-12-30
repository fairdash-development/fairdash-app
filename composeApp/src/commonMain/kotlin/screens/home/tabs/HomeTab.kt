package screens.home.tabs

import FairDashColors
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import lib.HttpManager
import screens.home.HomeScreen

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Outlined.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon,
                )
            }
        }

    @Composable
    fun LocationDisabledWidget() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error Icon",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Location is not enabled",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Please enable location in your device settings to receive weather updates",
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                )
            }
        }
    }

    @Composable
    fun WeatherWidget(modifier: Modifier) {

        val currentDay = 22
        val model = rememberScreenModel { HomeScreen.HomeScreenModel() }

        val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
        val permissionsController: PermissionsController =
            remember(factory) { factory.createPermissionsController() }
        LaunchedEffect(Unit) {
            try {
                permissionsController.providePermission(Permission.LOCATION)
            } catch (deniedAlways: DeniedAlwaysException) {
                model.isLocationDisabled = true
            } catch (denied: DeniedException) {
                model.isLocationDisabled = true
            }

            if(permissionsController.isPermissionGranted(Permission.LOCATION)) {
                model.weatherData = HttpManager.getWeather(52.4, 54.6)
            } else {
                model.isLocationDisabled = true
            }
        }
        AnimatedVisibility(model.weatherData.hourly.temperature.isNotEmpty() || model.isLocationDisabled) {
            Card(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxHeight(.5f),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = if (model.isLocationDisabled) Color(FairDashColors.ERROR.toArgb()) else Color(
                    0xFF0076FF
                )
            ) {
                Column(
                    modifier = Modifier.padding(32.dp)
                ) {
                    if(model.isLocationDisabled) {
                        LocationDisabledWidget()
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "London",
                                color = Color.White,
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Icon(
                                Icons.Default.Cloud,
                                contentDescription = "Weather Icon",
                                tint = Color.White
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (model.weatherData.hourly.temperature.isNotEmpty()) "${
                                    model.weatherData.hourly.temperature[currentDay]
                                }${model.weatherData.units.temperature}" else "Loading...",
                                color = Color.White,
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Column(
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = "Sunny",
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                                Text(
                                    text = if (model.weatherData.hourly.feelsLikeTemperature.isNotEmpty()) "Feels like ${
                                        model.weatherData.hourly.feelsLikeTemperature[currentDay]
                                    }${model.weatherData.units.temperature}" else "Loading...",
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ShortHomeWidget(title: String, icon: ImageVector, modifier: Modifier = Modifier) {
        Card(
            modifier = modifier.padding(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(32.dp)) {
                Icon(icon, contentDescription = title, tint = Color.Black)
                Text(
                    text = title,
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
            }
        }
    }

    @Composable
    fun LongHomeWidget(title: String, icon: ImageVector, modifier: Modifier = Modifier) {
        Card(
            modifier = modifier.padding(8.dp).fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(32.dp)) {
                Icon(icon, contentDescription = title, tint = Color.Black)
                Text(
                    text = title,
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
            }
        }
    }

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FairDashColors.PRIMARY)
                    .padding(8.dp)
            ) {
                Text(
                    "Fair Name - Day X of Y",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherWidget(Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LongHomeWidget("Widget 3", Icons.Default.Notifications, Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ShortHomeWidget("Widget 1", Icons.Default.Home, Modifier.weight(1f))
                    ShortHomeWidget("Widget 2", Icons.Default.Settings, Modifier.weight(1f))
                }
            }
        }
    }
}