package screens.home.tabs

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Festival
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object EventsTab : Tab {
    override val options: TabOptions
        @Composable

        get() {
            val icon = rememberVectorPainter(Icons.Outlined.Festival)


            return remember {
                TabOptions(
                    index = 0u,
                    title = "Events",
                    icon = icon,
                )} }

    @Composable
    override fun Content() {
        Text("Events")
    }
}