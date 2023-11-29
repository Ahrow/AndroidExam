package exam.storeapp.screens.order_history

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderIdAndDateGenerator {
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