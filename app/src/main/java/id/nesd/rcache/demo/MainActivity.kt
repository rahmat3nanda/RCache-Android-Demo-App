package id.nesd.rcache.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.ui.theme.RCacheDemoAndroidTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RCacheDemoAndroidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text(text = "RCache") })
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "RCache",
                        )
                    }
                    FloatingMenuHome()
                }
            }
        }
    }
}

@Composable
fun FloatingMenuHome() {
    val isMenuOpen = remember {
        mutableStateOf(false)
    }

    FloatingMenu(isMenuOpen = isMenuOpen) {
        FloatingActionButton(
            onClick = { /* Handle Action 1 */ },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Clear"
                )
                Text("Clear")
            }
        }
        FloatingActionButton(
            onClick = { /* Handle Action 1 */ },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = "Remove"
                )
                Text("Remove")
            }
        }
        FloatingActionButton(
            onClick = { /* Handle Action 1 */ },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.Info,
                    contentDescription = "Read"
                )
                Text("Read")
            }
        }
        FloatingActionButton(
            onClick = { /* Handle Action 1 */ },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.AddCircle,
                    contentDescription = "Save"
                )
                Text("Save")
            }
        }
        FloatingActionButton(
            onClick = { /* Handle Action 1 */ },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.Build,
                    contentDescription = "Key"
                )
                Text("Key")
            }
        }
    }
}