import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import screens.home.HomeScreen

object FairDashColors {
    val WHITE = Color(0xFFEAEAEA)
    val BACKGROUND = Color(0xFF333333)
    val ERROR = Color(0xFFFF0000)
    val WARNING = Color(0xFFFFE57F)
    val PRIMARY = Color(0xFF009FB7)
}


@Composable
fun App() {
    Box(modifier = Modifier.background(FairDashColors.BACKGROUND)) {
        MaterialTheme(
            colors = MaterialTheme.colors.copy(
                primary = FairDashColors.PRIMARY,
                secondary = FairDashColors.PRIMARY,
                background = FairDashColors.BACKGROUND,
                surface = FairDashColors.WHITE,
                onPrimary = FairDashColors.WHITE,
                onSecondary = FairDashColors.WHITE,
                onBackground = FairDashColors.WHITE,
                onSurface = FairDashColors.WHITE,
                error = FairDashColors.ERROR,
                primaryVariant = FairDashColors.PRIMARY,
                secondaryVariant = FairDashColors.PRIMARY,  
            )
        )
        {
            Navigator(HomeScreen()) {
                FadeTransition(it)
            }
        }
    }
}