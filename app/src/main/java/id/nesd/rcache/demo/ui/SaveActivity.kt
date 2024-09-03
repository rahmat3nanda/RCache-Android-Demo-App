package id.nesd.rcache.demo.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.contracts.SaveContract
import id.nesd.rcache.demo.models.DataType
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.KeyModel
import id.nesd.rcache.demo.models.SaveModel
import id.nesd.rcache.demo.models.StorageType
import id.nesd.rcache.demo.presenters.SavePresenter
import id.nesd.rcache.demo.ui.theme.RCacheDemoAndroidTheme
import id.nesd.rcache.demo.utils.FormHeaderView
import id.nesd.rcache.demo.utils.Router.route

class SaveActivity : ComponentActivity(), SaveContract.View {

    private var presenter: SaveContract.Presenter? = null

    private val keyModel = KeyModel()
    private val keys = mutableStateListOf<KeyItem>()

    private val dataType = mutableStateOf<DataType?>(null)
    private val key = mutableStateOf<KeyItem?>(null)
    private val storageType = mutableStateOf<StorageType?>(null)

    private val fieldValue = mutableStateOf("")
    private val buttonEnabled = mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = SavePresenter(this, SaveModel())

        enableEdgeToEdge()
        setContent {
            RCacheDemoAndroidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "RCache: Save") },
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
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FormHeaderView(
                            dataType = dataType.value,
                            key = key.value,
                            storageType = storageType.value,
                            showAddKey = true,
                            sourceDataType = DataType.entries,
                            sourceKey = keys,
                            sourceStorageType = StorageType.entries,
                            dataTypeChanged = {
                                dataType.value = it
                                fieldValue.value = ""
                                this@SaveActivity.hideKeyboard()
                                check()
                            },
                            keyChanged = {
                                key.value = it
                                check()
                            },
                            didAddKey = {
                                this@SaveActivity.route(to = KeyActivity::class.java)
                            },
                            storageTypeChanged = {
                                storageType.value = it
                                check()
                            }
                        )

                        HorizontalDivider()

                        Text("Value:")

                        if (dataType.value == DataType.BOOL) {
                            RadioButtonGroup(
                                items = listOf("TRUE", "FALSE"),
                                selectedItem = fieldValue.value,
                                onItemSelected = { value ->
                                    fieldValue.value = value
                                    check()
                                }
                            )
                        }

                        if (dataType.value?.isUseTextField() == true) {
                            val keyboardType = if (dataType.value?.isNumber() == true) {
                                KeyboardType.Decimal
                            } else {
                                KeyboardType.Text
                            }

                            TextField(
                                value = fieldValue.value,
                                onValueChange = { newValue ->
                                    fieldValue.value = newValue
                                    check()
                                },
                                label = { Text("Value") },
                                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))


                        Button(
                            onClick = { submit() },
                            enabled = buttonEnabled.value,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonColors(
                                containerColor = Color.Blue,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = "Save",
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        keyModel.loadItems()
        keys.clear()
        keys.addAll(keyModel.items)
        if (!keys.contains(key.value)) {
            key.value = null
        }

        check()
    }

    private fun check() {
        buttonEnabled.value =
            dataType.value != null && key.value != null && storageType.value != null && fieldValue.value.isNotEmpty()
    }

    private fun submit() {
        presenter?.save(
            dataType = dataType.value!!,
            key = key.value!!,
            storageType = storageType.value!!,
            value = fieldValue.value
        )
    }

    override fun failureSave(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun successSave() {
        fieldValue.value = ""
        check()
        Toast.makeText(this, "Success Saving", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun RadioButtonGroup(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Column {
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemSelected(item) }
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = item == selectedItem,
                    onClick = { onItemSelected(item) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item)
            }
        }
    }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}