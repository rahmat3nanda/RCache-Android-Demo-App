package id.nesd.rcache.demo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.ui.theme.RCacheDemoAndroidTheme
import id.nesd.rcache.demo.utils.FloatingMenu
import id.nesd.rcache.demo.utils.LogManager
import id.nesd.rcache.demo.utils.LogModel
import id.nesd.rcache.demo.utils.Router.route

class MainActivity : ComponentActivity() {

    private var logs = mutableStateListOf<LogModel>()

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

                    if (logs.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "RCache")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(innerPadding),
                            reverseLayout = true,
                            contentPadding = PaddingValues(12.dp)
                        ) {
                            items(logs) { log ->
                                HorizontalDivider()
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Date: ${log.time}\nAction: ${log.action}\nValue: ${log.value}"
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }

                    FloatingMenuHome(onClick = {
                        when (it) {
                            MenuRouting.KEY -> route(to = KeyActivity::class.java)

                            MenuRouting.REMOVE -> {

                            }

                            MenuRouting.SAVE -> route(to = SaveActivity::class.java)
                            MenuRouting.READ -> route(to = ReadActivity::class.java)

                            MenuRouting.CLEAR -> TODO()
                        }
                    })
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        logs.clear()
        logs.addAll(LogManager.instance.data() ?: emptyList())
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
            onClick = {
                isMenuOpen.value = false
                onClick(MenuRouting.CLEAR)
            }, modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Delete, contentDescription = "Clear"
                )
                Text("Clear")
            }
        }
        FloatingActionButton(
            onClick = {
                isMenuOpen.value = false
                onClick(MenuRouting.REMOVE)
            }, modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.Close, contentDescription = "Remove"
                )
                Text("Remove")
            }
        }
        FloatingActionButton(
            onClick = {
                isMenuOpen.value = false
                onClick(MenuRouting.READ)
            }, modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.Info, contentDescription = "Read"
                )
                Text("Read")
            }
        }
        FloatingActionButton(
            onClick = {
                isMenuOpen.value = false
                onClick(MenuRouting.SAVE)
            }, modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.AddCircle, contentDescription = "Save"
                )
                Text("Save")
            }
        }
        FloatingActionButton(
            onClick = {
                isMenuOpen.value = false
                onClick(MenuRouting.KEY)
            }, modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.Build, contentDescription = "Key"
                )
                Text("Key")
            }
        }
    }
}