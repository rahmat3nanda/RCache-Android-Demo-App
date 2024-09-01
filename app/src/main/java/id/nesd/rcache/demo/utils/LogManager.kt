package id.nesd.rcache.demo.utils

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
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

    fun data(): List<LogModel>? {
        val string = RCache.common.readString(AppRCacheKey.logs) ?: return null
        val listType = object : TypeToken<List<LogModel>>() {}.type
        val result: List<LogModel>? = Gson().fromJson(string, listType)
        if (result != null) {
            localData = result.toMutableList()
        }
        return result
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

        val json = Gson().toJson(localData)
        RCache.common.save(string = json, key = AppRCacheKey.logs)
    }
}