package id.nesd.rcache.demo.models

import id.nesd.rcache.RCache
import id.nesd.rcache.RCaching
import id.nesd.rcache.demo.SaveContract
import id.nesd.rcache.demo.utils.LogActionType
import id.nesd.rcache.demo.utils.LogManager

enum class DataType(val stringValue: String) {
    DATA("Data"),
    STRING("String"),
    BOOL("Boolean"),
    INTEGER("Integer"),
    ARRAY("Array"),
    DICTIONARY("Dictionary"),
    DOUBLE("Double"),
    FLOAT("Float");

    fun isNumber(): Boolean {
        return when (this) {
            INTEGER, DOUBLE, FLOAT -> true
            else -> false
        }
    }

    fun isUseTextField(): Boolean {
        return when (this) {
            BOOL, ARRAY, DICTIONARY -> false
            else -> true
        }
    }
}

enum class StorageType(val stringValue: String) {
    COMMON("General Data"), CREDENTIALS("Credentials Data");

    fun rCache(): RCaching {
        return when (this) {
            COMMON -> RCache.common
            CREDENTIALS -> RCache.credentials
        }
    }
}

class SaveModel : SaveContract.Model {
    override fun save(
        dataType: DataType,
        key: KeyItem,
        storageType: StorageType,
        value: String,
        completion: (Boolean, String?) -> Unit
    ) {
        when (dataType) {
            DataType.DATA -> {
                value.toByteArray(Charsets.UTF_8).let { data ->
                    storageType.rCache().save(data = data, key = key.rCacheKey())
                    addToLog(dataType, key, storageType, value)
                    completion(true, null)
                }
            }

            DataType.STRING -> {
                storageType.rCache().save(string = value, key = key.rCacheKey())
                completion(true, null)
            }

            DataType.BOOL -> {
                value.boolValue()?.let { bool ->
                    storageType.rCache().save(bool = bool, key = key.rCacheKey())
                    addToLog(dataType, key, storageType, value)
                    completion(true, null)
                } ?: completion(false, "Invalid Boolean")
            }

            DataType.INTEGER -> {
                value.toIntOrNull()?.let { int ->
                    storageType.rCache().save(int, key.rCacheKey())
                    addToLog(dataType, key, storageType, value)
                    completion(true, null)
                } ?: completion(false, "Invalid Integer")
            }

            DataType.ARRAY, DataType.DICTIONARY -> completion(false, "Not Implemented")

            DataType.DOUBLE -> {
                value.toDoubleOrNull()?.let { double ->
                    storageType.rCache().save(double, key.rCacheKey())
                    addToLog(dataType, key, storageType, value)
                    completion(true, null)
                } ?: completion(false, "Invalid Double")
            }

            DataType.FLOAT -> {
                value.toFloatOrNull()?.let { float ->
                    storageType.rCache().save(float, key.rCacheKey())
                    addToLog(dataType, key, storageType, value)
                    completion(true, null)
                } ?: completion(false, "Invalid Float")
            }
        }
    }

    private fun addToLog(
        dataType: DataType,
        key: KeyItem,
        storageType: StorageType,
        value: String
    ) {
        LogManager.instance.add(
            action = LogActionType.SAVE,
            value = "\n-Data Type: ${dataType.stringValue}\n-Key: ${key.name}\n-Storage Type: ${storageType.stringValue}\n-Value: $value"
        )
    }

    private fun String.boolValue(): Boolean? {
        return when (this.lowercase()) {
            "true" -> true
            "false" -> false
            else -> null
        }
    }
}