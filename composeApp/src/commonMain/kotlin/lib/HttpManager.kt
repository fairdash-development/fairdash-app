package lib

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

object HttpManager {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    private const val WEATHER_API_URL = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m,apparent_temperature,precipitation_probability,precipitation&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch&timeformat=unixtime&timezone=America%2FNew_York"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }


    @Serializable
    data class WeatherData (
        val time: List<Int>,
        @SerialName("temperature_2m") val temperature: List<Double>,
        @SerialName("apparent_temperature") val feelsLikeTemperature: List<Double>,
        val precipitation: List<Double>,
        @SerialName("precipitation_probability") val chance: List<Int>,
    )

    @Serializable
    data class UnitData (
        @SerialName("temperature_2m") val temperature: String,
        @SerialName("apparent_temperature_2m") val feelsLikeTemperature: String,
        val precipitation: String,
        @SerialName("precipitation_percent") val chance: String,
    )

    @Serializable
    data class WeatherResponse(
        val hourly: WeatherData,
        @SerialName("hourly_units") val units: UnitData
    )
    suspend fun getWeather(): WeatherResponse {
        var res: WeatherResponse = WeatherResponse(WeatherData(listOf(), listOf(), listOf(), listOf(), listOf()), UnitData("", "", "", ""))
        try {
            res = httpClient.get(WEATHER_API_URL).body()
        } catch (e: Exception) {
            println(e)
        }
        return res
    }
}