package np.com.lashman.learnit.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import np.com.lashman.learnit.domain.model.Subject

@Composable
fun AddSubjectDialog(
    isOpen: Boolean,
    title: String = "Add/Update Subject",
    selectedColors: List<Color>,
    subjectName: String,
    goalHours: String,
    onColorChange: (List<Color>) -> Unit,
    onSubjectNameChange: (String) -> Unit,
    onGoalHoursChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    // State variables to manage errors for subject name and goal hours
    var subjectNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var goalHoursError by rememberSaveable { mutableStateOf<String?>(null) }

    // Validation for subject name: checks for length and blank input
    subjectNameError = when {
        subjectName.isBlank() -> "Please enter subject name."
        subjectName.length < 2 -> "Subject name is too short."
        subjectName.length > 20 -> "Subject name is too long."
        else -> null
    }

    // Validation for goal hours: checks for blank, non-numeric, and out-of-range values
    goalHoursError = when {
        goalHours.isBlank() -> "Please enter goal study hours."
        goalHours.toFloatOrNull() == null -> "Invalid number."
        goalHours.toFloat() < 1f -> "Please set at least 1 hour."
        goalHours.toFloat() > 1000f -> "Please set a maximum of 1000 hours."
        else -> null
    }

    // Display the dialog if isOpen is true
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,  // Function to dismiss the dialog
            title = { Text(text = title) },  // Title of the dialog
            text = {
                Column {
                    // Color selection row with circular boxes for each color
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        // Iterates through predefined colors to display circular boxes
                        Subject.subjectCardColors.forEach { colors ->
                            Box(
                                modifier = Modifier
                                    .size(24.dp)  // Circle size
                                    .clip(CircleShape)  // Clip to circle shape
                                    .border(
                                        width = 1.dp,
                                        color = if (colors == selectedColors) Color.Black
                                        else Color.Transparent,  // Border for selected color
                                        shape = CircleShape
                                    )
                                    .background(brush = Brush.verticalGradient(colors))  // Gradient color background
                                    .clickable { onColorChange(colors) }  // On click, change color
                            )
                        }
                    }
                    // Subject name input field with validation error message if applicable
                    OutlinedTextField(
                        value = subjectName,
                        onValueChange = onSubjectNameChange,  // Callback to update subject name
                        label = { Text(text = "Subject Name") },  // Label for input field
                        singleLine = true,
                        isError = subjectNameError != null && subjectName.isNotBlank(),  // Show error if validation fails
                        supportingText = { Text(text = subjectNameError.orEmpty())}  // Display error message
                    )
                    Spacer(modifier = Modifier.height(10.dp))  // Spacer between fields
                    // Goal hours input field with validation error message if applicable
                    OutlinedTextField(
                        value = goalHours,
                        onValueChange = onGoalHoursChange,  // Callback to update goal hours
                        label = { Text(text = "Goal Study Hours") },  // Label for input field
                        singleLine = true,
                        isError = goalHoursError != null && goalHours.isNotBlank(),  // Show error if validation fails
                        supportingText = { Text(text = goalHoursError.orEmpty())},  // Display error message
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)  // Numeric keyboard for goal hours input
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {  // Button to dismiss the dialog
                    Text(text = "Cancel")  // Cancel button text
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,  // Button to confirm and save the subject
                    enabled = subjectNameError == null && goalHoursError == null  // Enable button only if no errors
                ) {
                    Text(text = "Save")  // Save button text
                }
            }
        )
    }
}
