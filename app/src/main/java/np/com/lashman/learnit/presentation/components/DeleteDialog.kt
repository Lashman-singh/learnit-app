package np.com.lashman.learnit.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    isOpen: Boolean,  // Boolean to determine if the dialog should be visible
    title: String,  // Title of the dialog
    bodyText: String,  // Text to display in the body of the dialog
    onDismissRequest: () -> Unit,  // Callback when the dialog is dismissed
    onConfirmButtonClick: () -> Unit  // Callback when the "Delete" button is clicked
) {
    if (isOpen) {  // Show the dialog only when isOpen is true
        AlertDialog(
            onDismissRequest = onDismissRequest,  // Define the behavior when the dialog is dismissed
            title = { Text(text = title) },  // Display the title of the dialog
            text = { Text(text = bodyText) },  // Display the body text in the dialog
            dismissButton = {  // Add a dismiss button with the "Cancel" action
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {  // Add a confirm button with the "Delete" action
                TextButton(onClick = onConfirmButtonClick) {
                    Text(text = "Delete")
                }
            }
        )
    }
}