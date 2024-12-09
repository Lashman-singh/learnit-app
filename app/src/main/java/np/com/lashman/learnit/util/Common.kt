package np.com.lashman.learnit.util

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import np.com.lashman.learnit.presentation.theme.Green
import np.com.lashman.learnit.presentation.theme.Orange
import np.com.lashman.learnit.presentation.theme.Red
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Enum class representing task priority levels with their associated color and value
enum class Priority(val title: String, val color: Color, val value: Int) {
    LOW(title = "Low", color = Green, value = 0),      // Low priority
    MEDIUM(title = "Medium", color = Orange, value = 1),  // Medium priority
    HIGH(title = "High", color = Red, value = 2);      // High priority

    // Companion object to map an integer value to a Priority enum
    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.value == value } ?: MEDIUM
    }
}

// Extension function to convert a millisecond timestamp to a formatted date string (dd MMM yyyy)
fun Long?.changeMillisToDateString(): String {
    val date: LocalDate = this?.let {
        Instant
            .ofEpochMilli(it)  // Convert timestamp to Instant
            .atZone(ZoneId.systemDefault())  // Convert to the system's default time zone
            .toLocalDate()  // Convert to LocalDate
    } ?: LocalDate.now()  // If null, return today's date
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))  // Format the date
}

// Extension function to convert a time duration in seconds (Long) to hours (Float), rounded to two decimal places
fun Long.toHours(): Float {
    val hours = this.toFloat() / 3600f  // Convert seconds to hours
    return "%.2f".format(hours).toFloat()  // Return the formatted float value
}

// Sealed class to represent different types of Snackbar events (show message or navigate up)
sealed class SnackbarEvent {
    data class ShowSnackbar(
        val message: String,  // Message to display
        val duration: SnackbarDuration = SnackbarDuration.Short  // Duration to display the message
    ) : SnackbarEvent()

    data object NavigateUp: SnackbarEvent()  // Represents navigating up (e.g., closing the screen)
}

// Extension function to pad an integer with leading zeros to ensure it has at least two digits
fun Int.pad(): String {
    return this.toString().padStart(length = 2, padChar = '0')  // Ensure the number has at least 2 digits
}
