package screens.home.tabs

import FairDashColors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Outlined.Settings)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Settings",
                    icon = icon,
                )
            }
        }

    @Composable
    fun SettingsItem(title: String, icon: ImageVector) {
        Divider(thickness = 1.dp)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f)) // Add this line
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "More",
                modifier = Modifier.size(24.dp)
            )
        }
    }

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FairDashColors.PRIMARY)
                    .padding(8.dp)
            ) {
                Text(
                    "Settings",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                SettingsItem("Manage Account", Icons.Default.ManageAccounts)
                SettingsItem("Delivery Location", Icons.Default.MyLocation)
                SettingsItem("Weather Preferences", Icons.Default.Cloud)
                SettingsItem("Billing Information", Icons.Default.ReceiptLong)
                SettingsItem("Notifications", Icons.Default.Notifications)
                SettingsItem("Privacy", Icons.Default.PrivacyTip)
                SettingsItem("Log Out", Icons.Default.Logout)
            }
        }
    }
}