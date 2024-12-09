package np.com.lashman.learnit.presentation.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)  // Opt-in annotation to use experimental Material3 APIs
@Composable
fun TaskDatePicker(
    state: DatePickerState,  // The state of the date picker
    isOpen: Boolean,  // Flag to control the visibility of the date picker dialog
    confirmButtonText: String = "OK",  // Text to display on the confirm button
    dismissButtonText: String = "Cancel",  // Text to display on the dismiss button
    onDismissRequest: () -> Unit,  // Callback when the date picker is dismissed
    onConfirmButtonClicked: () -> Unit  // Callback when the confirm button is clicked
) {
    if (isOpen) {  // Check if the date picker dialog is open
        // DatePickerDialog for displaying the date picker
        DatePickerDialog(
            onDismissRequest = onDismissRequest,  // Handle dismiss action
            confirmButton = {  // Confirm button action
                TextButton(onClick = onConfirmButtonClicked) {
                    Text(text = confirmButtonText)  // Text of the confirm button
                }
            },
            dismissButton = {  // Dismiss button action
                TextButton(onClick = onDismissRequest) {
                    Text(text = dismissButtonText)  // Text of the dismiss button
                }
            }
        ) {
            // Remember the selected date to validate its correctness
            val isValidDate = remember(state.selectedDateMillis) {
                state.selectedDateMillis?.let {
                    val selectedDate = Instant.ofEpochMilli(it)  // Convert milliseconds to Instant
                        .atZone(ZoneId.systemDefault())  // Convert to system's time zone
                        .toLocalDate()  // Convert Instant to LocalDate
                    selectedDate >= LocalDate.now()  // Check if the selected date is today or in the future
                } ?: false  // If no date is selected, return false
            }

            // Another DatePickerDialog with validation on the confirm button
            DatePickerDialog(
                onDismissRequest = onDismissRequest,  // Handle dismiss action
                confirmButton = {  // Confirm button action
                    TextButton(
                        onClick = onConfirmButtonClicked,  // On click, trigger the confirm callback
                        enabled = isValidDate  // Enable or disable the confirm button based on date validity
                    ) {
                        Text(text = confirmButtonText)  // Text of the confirm button
                    }
                },
                dismissButton = {  // Dismiss button action
                    TextButton(onClick = onDismissRequest) {
                        Text(text = dismissButtonText)  // Text of the dismiss button
                    }
                }
            ) {
                DatePicker(state = state)  // Display the date picker component with the current state
            }

        }
    }
}
