package id.nesd.rcache.demo.models

import id.nesd.rcache.RCache
import id.nesd.rcache.demo.AppRCacheKey
import id.nesd.rcache.demo.KeyContract
import id.nesd.rcache.demo.utils.LogActionType
import id.nesd.rcache.demo.utils.LogManager

data class KeyItem(val name: String) {
    fun rCacheKey(): RCache.Key {
        return RCache.Key(name)
    }
}

class KeyModel : KeyContract.Model<KeyItem> {
    override val items: MutableList<KeyItem> = mutableListOf()

    override fun loadItems() {
        val keys: List<String>? = RCache.common.readArray(AppRCacheKey.savedKeys)
        if (keys != null) {
            items.clear()
            items.addAll(keys.map { KeyItem(name = it) })
        }
    }

    override fun add(item: KeyItem) {
        items.add(item)
        RCache.common.save(array = items.map { it.name }, key = AppRCacheKey.savedKeys)
        LogManager.instance.add(action = LogActionType.ADD_KEY, value = item.name)
    }

    override fun delete(at: Int) {
        LogManager.instance.add(action = LogActionType.REMOVE_KEY, value = items[at].name)
        items.removeAt(at)
        RCache.common.save(array = items.map { it.name }, key = AppRCacheKey.savedKeys)
    }
}