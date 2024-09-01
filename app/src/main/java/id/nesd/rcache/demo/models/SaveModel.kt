package id.nesd.rcache.demo.models

import id.nesd.rcache.RCache
import id.nesd.rcache.RCaching

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