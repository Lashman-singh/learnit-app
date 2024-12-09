package np.com.lashman.learnit.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskCheckBox(
    isComplete: Boolean,  // A flag indicating if the task is complete
    borderColor: Color,  // The color of the border around the checkbox
    onCheckBoxClick: () -> Unit  // The function to be called when the checkbox is clicked
) {
    Box(
        modifier = Modifier
            .size(25.dp)  // Define the size of the checkbox
            .clip(CircleShape)  // Clip the box to a circular shape
            .border(2.dp, borderColor, CircleShape)  // Apply a border with the specified color and thickness
            .clickable { onCheckBoxClick() },  // Handle the click event on the checkbox
        contentAlignment = Alignment.Center  // Center the content (icon) inside the box
    ) {
        AnimatedVisibility(visible = isComplete) {  // Display the check icon if the task is complete
            Icon(
                modifier = Modifier.size(20.dp),  // Define the size of the check icon
                imageVector = Icons.Rounded.Check,  // Use the check icon from Material Icons
                contentDescription = null  // No content description for the icon
            )
        }
    }
}