package id.nesd.rcache.demo.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.models.DataType
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.StorageType

@Composable
fun FormHeaderView(
    modifier: Modifier = Modifier,
    dataType: DataType? = null,
    key: KeyItem?,
    storageType: StorageType?,
    showAddKey: Boolean = false,
    showDataType: Boolean = true,
    sourceDataType: List<DataType>,
    sourceKey: List<KeyItem>,
    sourceStorageType: List<StorageType>,
    dataTypeChanged: ((DataType) -> Unit)? = null,
    keyChanged: ((KeyItem) -> Unit)? = null,
    didAddKey: (() -> Unit)? = null,
    storageTypeChanged: ((StorageType) -> Unit)? = null
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showDataType) {
            DropdownPicker(
                title = "Data Type:",
                placeholder = "Select Data Type",
                selected = dataType,
                sources = sourceDataType,
                titleForItem = { it.stringValue },
                onChanged = {
                    if (dataTypeChanged != null) {
                        dataTypeChanged(it)
                    }
                }
            )
        }

        DropdownPicker(
            title = "Key:",
            placeholder = "Select Key",
            selected = key,
            sources = sourceKey,
            titleForItem = { it.name },
            suffix = {
                if (showAddKey) {
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier.clickable {
                            if (didAddKey != null) {
                                didAddKey()
                            }
                        }
                    )
                }
            },
            onChanged = {
                if (keyChanged != null) {
                    keyChanged(it)
                }
            }
        )

        DropdownPicker(
            title = "Storage Type:",
            placeholder = "Select Storage Type",
            selected = storageType,
            sources = sourceStorageType,
            titleForItem = { it.stringValue },
            onChanged = {
                if (storageTypeChanged != null) {
                    storageTypeChanged(it)
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ContentView() {
    var dataType: DataType? by remember { mutableStateOf(null) }
    var key: KeyItem? by remember { mutableStateOf(null) }
    var storageType: StorageType? by remember { mutableStateOf(null) }

    FormHeaderView(
        dataType = dataType,
        key = key,
        storageType = storageType,
        showAddKey = true,
        showDataType = true,
        sourceDataType = DataType.entries,
        sourceKey = listOf(KeyItem(name = "Tes 1"), KeyItem(name = "Tes 2")),
        sourceStorageType = StorageType.entries,
        dataTypeChanged = {
            dataType = it
        },
        keyChanged = {
            key = it
        },
        didAddKey = {

        },
        storageTypeChanged = {
            storageType = it
        }
    )
}