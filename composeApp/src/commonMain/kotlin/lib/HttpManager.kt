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

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private fun getWeatherApiUrl(lat: Double, long: Double) = "https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$long&hourly=temperature_2m,apparent_temperature,precipitation_probability,precipitation&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch&timeformat=unixtime&timezone=America%2FNew_York"

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
        @SerialName("apparent_temperature") val feelsLikeTemperature: String,
        val precipitation: String,
        @SerialName("precipitation_probability") val chance: String,
    )

    @Serializable
    data class WeatherResponse(
        val hourly: WeatherData,
        @SerialName("hourly_units") val units: UnitData
    )
    suspend fun getWeather(lat: Double, long: Double): WeatherResponse = httpClient.get(getWeatherApiUrl(lat, long)).body()

}