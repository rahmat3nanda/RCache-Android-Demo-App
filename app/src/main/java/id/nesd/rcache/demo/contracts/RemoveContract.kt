package id.nesd.rcache.demo.contracts

import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.StorageType

interface RemoveContract {
    interface View

    interface Model {
        fun remove(key: KeyItem, storageType: StorageType)
    }

    interface Presenter {
        fun remove(key: KeyItem, storageType: StorageType)
    }
}