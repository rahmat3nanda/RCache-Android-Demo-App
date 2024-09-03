package id.nesd.rcache.demo.presenters

import id.nesd.rcache.demo.contracts.ReadContract
import id.nesd.rcache.demo.models.DataType
import id.nesd.rcache.demo.models.KeyItem
import id.nesd.rcache.demo.models.StorageType

class ReadPresenter(private var view: ReadContract.View, private val model: ReadContract.Model) :
    ReadContract.Presenter {

    override fun read(key: KeyItem, storageType: StorageType, dataType: DataType) {
        model.read(key = key, storageType = storageType, dataType = dataType) {
            view.readResult(it)
        }
    }
}