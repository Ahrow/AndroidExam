package exam.storeapp.screens.order_history

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderIdAndDateGenerator {
    //TODO maybe remove when DB implemented -> AUTO increment
    companion object {
        private var lastId = 0

        fun generateOrderId(): Int {
            return lastId++
        }
        fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }
}