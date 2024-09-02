package id.nesd.rcache.demo.presenters

import id.nesd.rcache.demo.SaveContract
import id.nesd.rcache.demo.models.DataType
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.StorageType

class SavePresenter(private var view: SaveContract.View, private val model: SaveContract.Model) :
    SaveContract.Presenter {

    override fun save(dataType: DataType, key: KeyItem, storageType: StorageType, value: String) {
        model.save(
            dataType = dataType,
            key = key,
            storageType = storageType,
            value = value
        ) { success, message ->
            if (success) {
                view.successSave()
            } else {
                view.failureSave(message = message ?: "Unknown Error")
            }
        }
    }
}