package id.nesd.rcache.demo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.contracts.ClearContract
import id.nesd.rcache.demo.models.ClearModel
import id.nesd.rcache.demo.models.ClearType
import id.nesd.rcache.demo.models.StorageType
import id.nesd.rcache.demo.presenters.ClearPresenter
import id.nesd.rcache.demo.utils.AppScaffold

class ClearActivity : ComponentActivity(), ClearContract.View {

    private var presenter: ClearContract.Presenter? = null

    private val clearType = mutableStateOf<ClearType?>(null)
    private val clearTypeExpanded = mutableStateOf(false)
    private val buttonEnabled = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ClearPresenter(this, ClearModel())

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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Clear Type:")
                        Spacer(modifier = Modifier.weight(1f))
                        Box {
                            Text(
                                text = clearType.value?.stringValue ?: "Select Clear Type",
                                color = Color.Blue,
                                modifier = Modifier
                                    .clickable {
                                        clearTypeExpanded.value = !clearTypeExpanded.value
                                    }
                                    .padding(vertical = 8.dp)
                            )
                            DropdownMenu(
                                expanded = clearTypeExpanded.value,
                                onDismissRequest = { clearTypeExpanded.value = false }
                            ) {
                                ClearType.entries.forEach {
                                    DropdownMenuItem(
                                        text = { Text(text = it.stringValue) },
                                        leadingIcon = {
                                            if (clearType.value?.stringValue == it.stringValue) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                )
                                            }
                                        },
                                        onClick = {
                                            clearTypeExpanded.value = false
                                            clearType.value = it
                                            check()
                                        }
                                    )
                                }
                            }
                        }
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
                            text = "Clear",
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }

    private fun check() {
        buttonEnabled.value = clearType.value != null
    }

    private fun submit() {
        presenter?.clear(clearType.value!!)
        Toast.makeText(
            this,
            "Success Clear ${clearType.value!!.stringValue}",
            Toast.LENGTH_SHORT
        ).show()
    }
}