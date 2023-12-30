package screens.home.tabs

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object DeliveryTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Outlined.Fastfood)


            return remember {
                TabOptions(
                    index = 0u,
                    title = "Delivery",
                    icon = icon,
        )} }

    @Composable
    override fun Content() {
        Text("Delivery")
    }
}