package id.nesd.rcache.demo.utils

import android.content.Intent
import androidx.activity.ComponentActivity

object Router {
    fun <T : ComponentActivity> ComponentActivity.route(to: Class<T>) {
        this.startActivity(Intent(this, to))
    }
}