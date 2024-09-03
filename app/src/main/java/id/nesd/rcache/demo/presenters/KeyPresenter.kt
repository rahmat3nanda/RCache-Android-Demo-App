package id.nesd.rcache.demo.presenters

import id.nesd.rcache.demo.contracts.KeyContract
import id.nesd.rcache.demo.models.KeyItem

class KeyPresenter(
    private var view: KeyContract.View,
    private val model: KeyContract.Model<KeyItem>,
) : KeyContract.Presenter {
    override fun loadItems() {
        model.loadItems()
        view.dataChanged(model.items)
    }

    override fun add(item: String) {
        model.add(KeyItem(name = item))
        view.dataChanged(model.items)
    }

    override fun delete(item: KeyItem) {
        model.delete(model.items.indexOf(item))
        view.dataChanged(model.items)
    }
}