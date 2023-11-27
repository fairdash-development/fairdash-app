package composables

import FairDashColors
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class AlertType {
    ERROR,
    INFO,
    WARNING
}

@Composable
fun Alert(type: AlertType, text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = when (type) {
            AlertType.ERROR -> FairDashColors.ERROR
            AlertType.INFO -> FairDashColors.PRIMARY
            AlertType.WARNING -> FairDashColors.WARNING
        },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Icon(
                imageVector = when (type) {
                    AlertType.ERROR -> Icons.Default.Close
                    AlertType.INFO -> Icons.Default.Info
                    AlertType.WARNING -> Icons.Default.Warning
                },
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
            )
        }
    }
}