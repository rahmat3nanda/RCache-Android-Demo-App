package id.nesd.rcache.demo

import android.app.Application
import id.nesd.rcache.RCache

class RCacheApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RCache.initialize(this)
    }
}