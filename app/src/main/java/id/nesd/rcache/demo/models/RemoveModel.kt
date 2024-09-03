package id.nesd.rcache.demo.models

import id.nesd.rcache.demo.contracts.RemoveContract
import id.nesd.rcache.demo.utils.LogActionType
import id.nesd.rcache.demo.utils.LogManager

class RemoveModel : RemoveContract.Model {
    override fun remove(key: KeyItem, storageType: StorageType) {
        storageType.rCache().remove(key = key.rCacheKey())
        LogManager.instance.add(
            action = LogActionType.REMOVE,
            value = "\n-Key: ${key.name}\n-Storage Type: ${storageType.stringValue}"
        )
    }
}