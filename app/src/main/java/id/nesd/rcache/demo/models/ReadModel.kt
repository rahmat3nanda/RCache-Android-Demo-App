package id.nesd.rcache.demo.models

import id.nesd.rcache.demo.contracts.ReadContract
import id.nesd.rcache.demo.utils.LogActionType
import id.nesd.rcache.demo.utils.LogManager

class ReadModel : ReadContract.Model {
    override fun read(
        key: KeyItem,
        storageType: StorageType,
        dataType: DataType,
        completion: (String) -> Unit
    ) {
        when (dataType) {
            DataType.BYTE_ARRAY -> {
                val value = storageType.rCache().readByteArray(key = key.rCacheKey())
                val string = String(bytes = value ?: "null".toByteArray())
                completion(string)
                addToLog(dataType = dataType, key = key, storageType = storageType, value = string)
            }

            DataType.STRING -> {
                val value = storageType.rCache().readString(key = key.rCacheKey())
                val string = value ?: "null"
                completion(string)
                addToLog(dataType = dataType, key = key, storageType = storageType, value = string)
            }

            DataType.BOOL -> {
                val value = storageType.rCache().readBool(key = key.rCacheKey())
                val string = value?.toString() ?: "null"
                completion(string)
                addToLog(dataType = dataType, key = key, storageType = storageType, value = string)
            }

            DataType.INTEGER -> {
                val value = storageType.rCache().readInteger(key = key.rCacheKey())
                val string = value?.toString() ?: "null"
                completion(string)
                addToLog(dataType = dataType, key = key, storageType = storageType, value = string)
            }

            DataType.ARRAY, DataType.MAP -> completion("Not Implemented")

            DataType.DOUBLE -> {
                val value = storageType.rCache().readDouble(key = key.rCacheKey())
                val string = value?.toString() ?: "null"
                completion(string)
                addToLog(dataType = dataType, key = key, storageType = storageType, value = string)
            }

            DataType.FLOAT -> {
                val value = storageType.rCache().readFloat(key = key.rCacheKey())
                val string = value?.toString() ?: "null"
                completion(string)
                addToLog(dataType = dataType, key = key, storageType = storageType, value = string)
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
            action = LogActionType.READ,
            value = "\n-Data Type: ${dataType.stringValue}\n-Key: ${key.name}\n-Storage Type: ${storageType.stringValue}\n-Value: $value"
        )
    }
}