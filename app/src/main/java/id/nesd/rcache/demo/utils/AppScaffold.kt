package id.nesd.rcache.demo.utils

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.nesd.rcache.demo.ui.theme.RCacheDemoAndroidTheme
import id.nesd.rcache.demo.utils.Router.isRootIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    activity: ComponentActivity,
    modifier: Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {

    val name: String = if (!activity.isRootIntent()) {
        ": ${activity.localClassName.split(".").last().removeSuffix("Activity")}"
    } else ""

    RCacheDemoAndroidTheme {
        Scaffold(
            modifier = modifier,
            floatingActionButton = floatingActionButton,
            topBar = {
                TopAppBar(
                    title = { Text(text = "RCache${name}") },
                    navigationIcon = {
                        if (!activity.isRootIntent()) {
                            IconButton(onClick = { activity.finish() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    },
                )
            },
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}