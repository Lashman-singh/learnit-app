package np.com.lashman.learnit.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import np.com.lashman.learnit.R

@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,  // Modifier for customizing the card appearance
    subjectName: String,  // Name of the subject to be displayed
    gradientColors: List<Color>,  // List of colors to create a gradient background
    onClick: () -> Unit  // Callback for the click event of the card
) {
    Box(
        modifier = modifier
            .size(150.dp)  // Set the size of the card
            .clickable { onClick() }  // Make the card clickable and trigger the onClick callback
            .background(  // Set the background with a gradient brush
                brush = Brush.verticalGradient(gradientColors),
                shape = MaterialTheme.shapes.medium  // Apply a medium shape for rounded corners
            )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),  // Add padding inside the card
            verticalArrangement = Arrangement.Center  // Center the content vertically
        ) {
            Image(
                painter = painterResource(R.drawable.books),  // Load an image from resources
                contentDescription = subjectName,  // Provide a description for the image
                modifier = Modifier.size(80.dp)  // Set the size of the image
            )
            Text(
                text = subjectName,  // Display the subject name
                style = MaterialTheme.typography.headlineMedium,  // Apply the medium headline style
                color = Color.White,  // Set the text color to white
                maxLines = 1  // Limit the text to a single line
            )
        }
    }
}