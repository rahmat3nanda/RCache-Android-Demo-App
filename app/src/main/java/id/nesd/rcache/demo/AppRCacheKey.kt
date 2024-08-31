package id.nesd.rcache.demo

import id.nesd.rcache.RCache

object AppRCacheKey {
    val savedKeys: RCache.Key = RCache.Key("id.nesd.rcache.savedKeys")
    val logs: RCache.Key = RCache.Key("id.nesd.rcache.logs")
}