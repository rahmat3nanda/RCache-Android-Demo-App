package id.nesd.rcache.demo.utils

import android.content.Intent
import androidx.activity.ComponentActivity

object Router {
    fun <T : ComponentActivity> ComponentActivity.route(to: Class<T>) {
        startActivity(Intent(this, to))
    }

    fun ComponentActivity.isRootIntent(): Boolean {
        return intent?.flags?.and(Intent.FLAG_ACTIVITY_NEW_TASK) != 0 && isTaskRoot
    }
}