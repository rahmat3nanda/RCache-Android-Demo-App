package id.nesd.rcache.demo.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.models.KeyItem

@Composable
fun <T> DropdownPicker(
    title: String,
    placeholder: String,
    selected: T?,
    sources: List<T>,
    titleForItem: (T) -> String,
    suffix: (@Composable () -> Unit)? = null,
    onChanged: (T) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        Box {
            Text(
                text = if (selected == null) placeholder else titleForItem(selected),
                color = Color.Blue,
                modifier = Modifier
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(vertical = 8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                sources.forEach {
                    DropdownMenuItem(
                        text = { Text(text = titleForItem(it)) },
                        leadingIcon = {
                            if (selected == it) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                )
                            }
                        },
                        onClick = {
                            expanded = false
                            onChanged(it)
                        }
                    )
                }
            }
        }

        if (suffix != null) {
            Spacer(modifier = Modifier.width(2.dp))
            suffix()
        }
    }
}