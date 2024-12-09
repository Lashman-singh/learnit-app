package np.com.lashman.learnit.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountCard(
    modifier: Modifier = Modifier,  // Modifier for customizing the card's appearance
    headingText: String,  // Text to display as the heading of the card
    count: String  // Text to display as the count value
) {
    ElevatedCard(modifier = modifier) {  // Elevated card that wraps the content
        Column(
            modifier = Modifier
                .fillMaxWidth()  // Ensure the column fills the width
                .padding(horizontal = 4.dp, vertical = 12.dp),  // Padding around the content
            verticalArrangement = Arrangement.Center,  // Center content vertically
            horizontalAlignment = Alignment.CenterHorizontally  // Center content horizontally
        ) {
            Text(
                text = headingText,  // Display heading text
                style = MaterialTheme.typography.labelSmall  // Use the Material Theme's small label style
            )
            Spacer(modifier = Modifier.height(5.dp))  // Spacer between the heading and count
            Text(
                text = count,  // Display count value
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 30.sp)  // Display count with a larger font size
            )
        }
    }
}
