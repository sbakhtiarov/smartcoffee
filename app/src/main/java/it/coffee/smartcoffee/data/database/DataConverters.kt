package it.coffee.smartcoffee.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.coffee.smartcoffee.domain.model.BaseItem

class DataConverters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { list ->
            with(Gson()) {
                toJson(list)
            }
        }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { string ->
            with(Gson()) {
                fromJson(string, object : TypeToken<List<String>> () {}.type)
            }
        }
    }

    @TypeConverter
    fun fromBaseItemList(value: List<BaseItem>?) : String? {
        return value?.let { list ->
            with(Gson()) {
                toJson(list)
            }
        }
    }

    @TypeConverter
    fun toBaseItemList(value: String?): List<BaseItem>? {
        return value?.let { string ->
            with(Gson()) {
                fromJson(string, object : TypeToken<List<BaseItem>> () {}.type)
            }
        }
    }

}