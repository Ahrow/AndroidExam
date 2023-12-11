package exam.storeapp.utillity

class StringFormatter {
    companion object {
        // Function to truncate strings to a given length
        fun limitLength(text: String, maxLength: Int): String {
            return if (text.length > maxLength) {
                text.substring(0, maxLength) + "…"
            } else {
                text
            }
        }
    }
}