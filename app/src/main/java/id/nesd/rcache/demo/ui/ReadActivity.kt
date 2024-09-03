package id.nesd.rcache.demo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.contracts.ReadContract
import id.nesd.rcache.demo.models.DataType
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.KeyModel
import id.nesd.rcache.demo.models.ReadModel
import id.nesd.rcache.demo.models.StorageType
import id.nesd.rcache.demo.presenters.ReadPresenter
import id.nesd.rcache.demo.utils.AppScaffold
import id.nesd.rcache.demo.utils.FormHeaderView

class ReadActivity : ComponentActivity(), ReadContract.View {

    private var presenter: ReadContract.Presenter? = null
    private val string = mutableStateOf("")

    private val keyModel = KeyModel()
    private val keys = mutableStateListOf<KeyItem>()

    private val dataType = mutableStateOf<DataType?>(null)
    private val key = mutableStateOf<KeyItem?>(null)
    private val storageType = mutableStateOf<StorageType?>(null)

    private val buttonEnabled = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ReadPresenter(this, ReadModel())

        enableEdgeToEdge()
        setContent {
            AppScaffold(
                activity = this,
                modifier = Modifier.fillMaxSize()
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
                        sourceDataType = DataType.entries,
                        sourceKey = keys,
                        sourceStorageType = StorageType.entries,
                        dataTypeChanged = {
                            dataType.value = it
                            check()
                        },
                        keyChanged = {
                            key.value = it
                            check()
                        },
                        storageTypeChanged = {
                            storageType.value = it
                            check()
                        }
                    )

                    HorizontalDivider()

                    if (string.value.isNotEmpty()) {
                        Text("Result:\n${string.value}")
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
                            text = "Read",
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }

    override fun readResult(string: String) {
        this.string.value = string
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
            dataType.value != null && key.value != null && storageType.value != null
    }

    private fun submit() {
        presenter?.read(
            dataType = dataType.value!!,
            key = key.value!!,
            storageType = storageType.value!!
        )
    }
}