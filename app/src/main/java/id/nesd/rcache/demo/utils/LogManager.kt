package id.nesd.rcache.demo.utils

import com.google.gson.annotations.SerializedName
import id.nesd.rcache.RCache
import id.nesd.rcache.demo.AppRCacheKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class LogActionType(val stringValue: String) {
    ADD_KEY("Add Key"),
    REMOVE_KEY("Remove Key"),
    SAVE("Save"), READ("Read"),
    REMOVE("Remove"),
    CLEAR("Clear")
}

data class LogRootModel(
    val data: List<LogModel>
)

data class LogModel(
    @SerializedName("action") val action: String,
    @SerializedName("value") val value: String,
    @SerializedName("time") val time: String
)

class LogManager private constructor() {

    private var localData: MutableList<LogModel> = mutableListOf()

    companion object {
        @Volatile
        private var _instance: LogManager? = null
        private val lock = Any()

        val instance: LogManager
            get() {
                return _instance ?: synchronized(lock) {
                    _instance ?: LogManager().also { _instance = it }
                }
            }
    }

    fun data(): List<LogModel> {
        val data = RCache.common.readDataClass(
            key = AppRCacheKey.logs,
            classOfT = LogRootModel::class.java
        )
        if (data?.data != null) {
            localData = data.data.toMutableList()
        }
        return localData
    }

    fun add(action: LogActionType, value: String) {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        localData.add(
            LogModel(
                action = action.stringValue,
                value = value,
                time = dateFormatter.format(Date())
            )
        )

        RCache.common.save(dataClass = LogRootModel(localData), key = AppRCacheKey.logs)
    }
}