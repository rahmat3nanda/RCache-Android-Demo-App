package id.nesd.rcache.demo.presenters

import id.nesd.rcache.demo.contracts.RemoveContract
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.StorageType

class RemovePresenter(
    private var view: RemoveContract.View,
    private val model: RemoveContract.Model
) : RemoveContract.Presenter {
    override fun remove(key: KeyItem, storageType: StorageType) {
        model.remove(key = key, storageType = storageType)
    }
}