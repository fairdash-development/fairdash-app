package screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import tabs.DeliveryTab
import tabs.EventsTab
import tabs.HomeTab
import tabs.SettingsTab

class HomeScreen : Screen {
    override val key = uniqueScreenKey

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = {
                tab.options.icon.let {
                    if (it != null) {
                        Icon(it, contentDescription = tab.options.title)
                    }
                }
            },
            label = { Text(tab.options.title) },
        )
    }

    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                        BottomNavigation {
                            TabNavigationItem(HomeTab)
                            TabNavigationItem(EventsTab)
                            TabNavigationItem(DeliveryTab)
                            TabNavigationItem(SettingsTab)
                    }
                }
            )
        }
    }
}