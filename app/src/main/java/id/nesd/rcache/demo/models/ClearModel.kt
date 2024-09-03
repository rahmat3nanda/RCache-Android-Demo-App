package id.nesd.rcache.demo.models

import id.nesd.rcache.RCache
import id.nesd.rcache.demo.contracts.ClearContract
import id.nesd.rcache.demo.utils.LogActionType
import id.nesd.rcache.demo.utils.LogManager


enum class ClearType(val stringValue: String) {
    COMMON("All General Data"),
    CREDENTIALS("All Credentials Data"),
    ALL("All Data")
}

class ClearModel : ClearContract.Model {
    override fun clear(type: ClearType) {
        when (type) {
            ClearType.COMMON -> RCache.common.clear()
            ClearType.CREDENTIALS -> RCache.credentials.clear()
            ClearType.ALL -> RCache.clear()
        }

        addToLog(type)
    }

    private fun addToLog(type: ClearType) {
        LogManager.instance.add(action = LogActionType.CLEAR, value = "Clear ${type.stringValue}")
    }
}