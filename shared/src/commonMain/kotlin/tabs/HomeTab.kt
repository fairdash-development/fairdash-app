package tabs

import FairDashColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
                    ShortHomeWidget("Widget 1", Icons.Default.Home, Modifier.weight(1f))
                    ShortHomeWidget("Widget 2", Icons.Default.Settings, Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LongHomeWidget("Widget 3", Icons.Default.Notifications, Modifier.weight(1f))
                }
            }
        }
    }
}