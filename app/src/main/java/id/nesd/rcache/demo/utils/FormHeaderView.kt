package id.nesd.rcache.demo.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.models.DataType
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.StorageType
import java.security.Key


@Composable
fun FormHeaderView(
    modifier: Modifier = Modifier,
    dataType: DataType? = null,
    key: KeyItem? = null,
    storageType: StorageType? = null,
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

    var dataTypeExpanded by remember { mutableStateOf(false) }
    var keyExpanded by remember { mutableStateOf(false) }
    var storageTypeExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showDataType) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Data Type:")
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    Text(
                        text = dataType?.stringValue ?: "Select Data Type",
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable {
                                dataTypeExpanded = !dataTypeExpanded
                            }
                            .padding(vertical = 8.dp)
                    )
                    DropdownMenu(
                        expanded = dataTypeExpanded,
                        onDismissRequest = { dataTypeExpanded = false }
                    ) {
                        sourceDataType.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it.stringValue) },
                                leadingIcon = {
                                    if (dataType?.stringValue == it.stringValue) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                        )
                                    }
                                },
                                onClick = {
                                    dataTypeExpanded = false
                                    if (dataTypeChanged != null) {
                                        dataTypeChanged(it)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Key:")
            Spacer(modifier = Modifier.weight(1f))
            Box {
                Text(
                    text = key?.name ?: "Select Key",
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable {
                            keyExpanded = !keyExpanded
                        }
                        .padding(vertical = 8.dp)
                )
                DropdownMenu(
                    expanded = keyExpanded,
                    onDismissRequest = { keyExpanded = false }
                ) {
                    sourceKey.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.name) },
                            leadingIcon = {
                                if (key?.name == it.name) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                    )
                                }
                            },
                            onClick = {
                                keyExpanded = false
                                if (keyChanged != null) {
                                    keyChanged(it)
                                }
                            }
                        )
                    }
                }
            }
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
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Storage Type:")
            Spacer(modifier = Modifier.weight(1f))
            Box {
                Text(
                    text = storageType?.stringValue ?: "Select Storage Type",
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable {
                            storageTypeExpanded = !storageTypeExpanded
                        }
                        .padding(vertical = 8.dp)
                )
                DropdownMenu(
                    expanded = storageTypeExpanded,
                    onDismissRequest = { storageTypeExpanded = false }
                ) {
                    sourceStorageType.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.stringValue) },
                            leadingIcon = {
                                if (storageType?.stringValue == it.stringValue) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                    )
                                }
                            },
                            onClick = {
                                storageTypeExpanded = false
                                if (storageTypeChanged != null) {
                                    storageTypeChanged(it)
                                }
                            }
                        )
                    }
                }
            }
        }
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