package id.nesd.rcache.demo.ui

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.nesd.rcache.demo.contracts.ClearContract
import id.nesd.rcache.demo.models.ClearModel
import id.nesd.rcache.demo.models.ClearType
import id.nesd.rcache.demo.presenters.ClearPresenter
import id.nesd.rcache.demo.utils.AppScaffold
import id.nesd.rcache.demo.utils.DropdownPicker

class ClearActivity : ComponentActivity(), ClearContract.View {

    private var presenter: ClearContract.Presenter? = null

    private val clearType = mutableStateOf<ClearType?>(null)
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
                    DropdownPicker(
                        title = "Clear Type:",
                        placeholder = "Select Clear Type",
                        selected = clearType.value,
                        sources = ClearType.entries,
                        titleForItem = { it.stringValue }
                    ) {
                        clearType.value = it
                        check()
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