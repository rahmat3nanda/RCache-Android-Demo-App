package id.nesd.rcache.demo.presenters

import id.nesd.rcache.demo.contracts.ClearContract
import id.nesd.rcache.demo.models.ClearType

class ClearPresenter(private var view: ClearContract.View, private val model: ClearContract.Model) :
    ClearContract.Presenter {
    override fun clear(type: ClearType) {
        model.clear(type)
    }
}