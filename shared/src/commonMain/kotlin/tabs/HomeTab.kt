package tabs

import FairDashColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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


    @Serializable
    data class WeatherData (
        val time: List<Int>,
        @SerialName("temperature_2m") val temperature: List<Double>,
        @SerialName("apparent_temperature_2m") val feelsLikeTemperature: List<Double>,
        val rain: List<Double>,
        val showers: List<Double>,
        val snowfall: List<Double>,
        @SerialName("snow_depth") val snowDepth: List<Double>,
        @SerialName("wind_speed_10m") val windSpeed: List<Double>,
        @SerialName("wind_direction_10m") val windDirection: List<Double>,
    )

    @Serializable
    data class WeatherResponse(
        @SerialName("hourly") val data: WeatherData
    )

    @Composable
    fun WeatherWidget(modifier: Modifier) {
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxHeight(.5f),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0xFF0076FF)
        ) {
            Column(
                modifier = Modifier.padding(32.dp)
            ) {
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
                        text = "25°C",
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
                            style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                        )
                        Text(
                            text = "Feels like 25°C",
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        )
                    }
                }
                Text(
                    text = "Min 20°C / Max 30°C",
                    color = Color.White,
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
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
        suspend {
            val client = HttpClient()
            var res: HttpResponse? = null
            try {
                res =
                    client.get("https://api.open-meteo.com/v1/gfs?latitude=52.52&longitude=13.41&current=is_day&hourly=temperature_2m,apparent_temperature,rain,showers,snowfall,snow_depth,wind_speed_10m,wind_direction_10m&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch&timeformat=unixtime&timezone=America%2FNew_York&forecast_days=3")
            } catch (e: Exception) {
                println(e)
            } finally {
                client.close()
            }
            if (res != null && res.status.value == 200) {
                Json.decodeFromString<WeatherResponse>(res.bodyAsText())
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
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