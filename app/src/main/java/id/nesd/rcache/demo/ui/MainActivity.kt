package id.nesd.rcache.demo.ui

import android.content.Intent
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
import id.nesd.rcache.demo.utils.FloatingMenu

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
                    FloatingMenuHome(onClick = {
                        when (it) {
                            MenuRouting.KEY -> {
                                startActivity(Intent(this, KeyActivity::class.java))
                            }

                            MenuRouting.REMOVE -> {

                            }

                            MenuRouting.SAVE -> TODO()
                            MenuRouting.READ -> TODO()
                            MenuRouting.CLEAR -> TODO()
                        }
                    })
                }
            }
        }
    }
}

enum class MenuRouting {
    KEY, SAVE, READ, REMOVE, CLEAR
}

@Composable
fun FloatingMenuHome(onClick: (MenuRouting) -> Unit) {
    val isMenuOpen = remember {
        mutableStateOf(false)
    }

    FloatingMenu(isMenuOpen = isMenuOpen) {
        FloatingActionButton(
            onClick = { onClick(MenuRouting.CLEAR) },
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
            onClick = { onClick(MenuRouting.REMOVE) },
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
            onClick = { onClick(MenuRouting.READ) },
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
            onClick = { onClick(MenuRouting.SAVE) },
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
            onClick = { onClick(MenuRouting.KEY) },
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