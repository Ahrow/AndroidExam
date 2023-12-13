package exam.storeapp.utillity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import exam.storeapp.data.models.OrderItem

class OrderItemTypeConverter {
    // SQLlite does no support complex objects directly -> Convert to JSON string
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