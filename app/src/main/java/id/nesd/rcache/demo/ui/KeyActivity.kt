package id.nesd.rcache.demo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import id.nesd.rcache.demo.ui.theme.RCacheDemoAndroidTheme

class KeyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RCacheDemoAndroidTheme {
                Scaffold { innerPadding ->
                    Text("tes")
                }
            }
        }
    }
}