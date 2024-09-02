package id.nesd.rcache.demo

import id.nesd.rcache.demo.models.DataType
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.StorageType

interface SaveContract {
    interface View {
        fun successSave()
        fun failureSave(message: String)
    }

    interface Model {
        fun save(
            dataType: DataType,
            key: KeyItem,
            storageType: StorageType,
            value: String,
            completion: (Boolean, String?) -> Unit
        )
    }

    interface Presenter {
        fun save(dataType: DataType, key: KeyItem, storageType: StorageType, value: String)
    }
}