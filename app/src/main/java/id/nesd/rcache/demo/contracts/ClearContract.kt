package id.nesd.rcache.demo.contracts

import id.nesd.rcache.demo.models.ClearType

interface ClearContract {
    interface View

    interface Model {
        fun clear(type: ClearType)
    }

    interface Presenter {
        fun clear(type: ClearType)
    }
}