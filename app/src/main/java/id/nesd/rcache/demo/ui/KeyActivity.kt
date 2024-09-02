package id.nesd.rcache.demo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.KeyContract
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.KeyModel
import id.nesd.rcache.demo.presenters.KeyPresenter
import id.nesd.rcache.demo.ui.theme.RCacheDemoAndroidTheme
import id.nesd.rcache.demo.utils.DismissibleList

class KeyActivity : ComponentActivity(), KeyContract.View {

    private var presenter: KeyContract.Presenter? = null
    private var showAlert: MutableState<Boolean> = mutableStateOf(false)
    private val data = mutableStateListOf<KeyItem>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = KeyPresenter(this, KeyModel())
        presenter?.loadItems()

        enableEdgeToEdge()
        setContent {
            RCacheDemoAndroidTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "RCache: Key") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            },
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { showAlert.value = true },
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Icon(
                                Icons.Rounded.Add,
                                contentDescription = "Remove"
                            )
                        }
                    }
                ) { innerPadding ->

                    if (data.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Empty")
                        }
                    } else {
                        DismissibleList(
                            items = data,
                            modifier = Modifier
                                .padding(innerPadding),
                            onDismiss = { item ->
                                presenter?.delete(item)
                            }
                        ) { item ->
                            ListItem(
                                headlineContent = {
                                    Text(text = item.name)
                                },
                            )
                        }
                    }

                    AddKeyDialog(showAlert = showAlert) {
                        if (it.trim().isEmpty()) {
                            Toast.makeText(this@KeyActivity, "Cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            presenter?.add(it.trim())
                        }
                    }
                }
            }
        }
    }

    override fun dataChanged(items: List<KeyItem>) {
        data.clear()
        data.addAll(items)
    }
}

@Composable
fun AddKeyDialog(
    showAlert: MutableState<Boolean>,
    onSave: (String) -> Unit
) {
    var key by remember { mutableStateOf("") }

    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = {
                showAlert.value = false
            },
            title = {
                Text(text = "Add Key")
            },
            text = {
                TextField(
                    value = key,
                    onValueChange = { key = it },
                    label = { Text("Key") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showAlert.value = false
                        onSave(key)
                        key = ""
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        key = ""
                        showAlert.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}