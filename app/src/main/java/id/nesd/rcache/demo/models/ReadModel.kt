package id.nesd.rcache.demo.models

import id.nesd.rcache.demo.contracts.ReadContract

class ReadModel : ReadContract.Model {
    override fun read(
        key: KeyItem,
        storageType: StorageType,
        dataType: DataType,
        completion: (String) -> Unit
    ) {
        when (dataType) {
            DataType.DATA -> {
                val value = storageType.rCache().readByteArray(key = key.rCacheKey())
                completion(String(bytes = value ?: "null".toByteArray()))
            }

            DataType.STRING -> {
                val value = storageType.rCache().readString(key = key.rCacheKey())
                completion(value ?: "null")
            }

            DataType.BOOL -> {
                val value = storageType.rCache().readBool(key = key.rCacheKey())
                completion(value?.toString() ?: "null")
            }

            DataType.INTEGER -> {
                val value = storageType.rCache().readInteger(key = key.rCacheKey())
                completion(value?.toString() ?: "null")
            }

            DataType.ARRAY, DataType.DICTIONARY -> TODO()

            DataType.DOUBLE -> {
                val value = storageType.rCache().readDouble(key = key.rCacheKey())
                completion(value?.toString() ?: "null")
            }

            DataType.FLOAT -> {
                val value = storageType.rCache().readFloat(key = key.rCacheKey())
                completion(value?.toString() ?: "null")
            }
        }
    }
}