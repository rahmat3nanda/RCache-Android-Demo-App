package id.nesd.rcache.demo.contracts

import id.nesd.rcache.demo.models.KeyItem

interface KeyContract {
    interface View {
        fun dataChanged(items: List<KeyItem>)
    }

    interface Model<T> {
        val items: MutableList<T>

        fun loadItems()
        fun add(item: T)
        fun delete(at: Int)
    }

    interface Presenter {
        fun loadItems()
        fun add(item: String)
        fun delete(item: KeyItem)
    }
}