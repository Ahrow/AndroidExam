package exam.storeapp.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import exam.storeapp.data.OrderItem

class OrderItemTypeConverter {
    // SQLlite does no support complex objects directly -> Convert to JSON string
    // https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.6 used
    // https://developer.android.com/training/data-storage/room/referencing-data
    @TypeConverter
    fun fromOrderItemList(items: List<OrderItem>): String {
        return Gson().toJson(items)
    }

    @TypeConverter
    fun toOrderItemList(itemString: String): List<OrderItem> {
        val itemType = object : TypeToken<List<OrderItem>>() {}.type
        return Gson().fromJson(itemString, itemType)
    }
}