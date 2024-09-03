package id.nesd.rcache.demo.contracts

import id.nesd.rcache.demo.models.DataType
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.StorageType

interface ReadContract {
    interface View {
        fun readResult(string: String)
    }

    interface Model {
        fun read(key: KeyItem, storageType: StorageType, dataType: DataType)
    }

    interface Presenter {
        fun read(key: KeyItem, storageType: StorageType, dataType: DataType)
    }
}