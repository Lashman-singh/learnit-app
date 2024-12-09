package np.com.lashman.learnit.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import np.com.lashman.learnit.domain.model.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectListBottomSheet(
    sheetState: SheetState,  // State of the bottom sheet to control its visibility and behavior
    isOpen: Boolean,  // Flag indicating if the bottom sheet is open
    subjects: List<Subject>,  // List of subjects to display in the bottom sheet
    bottomSheetTitle: String = "Related to subject",  // Title of the bottom sheet
    onSubjectClicked: (Subject) -> Unit,  // Callback when a subject is clicked
    onDismissRequest: () -> Unit  // Callback when the bottom sheet is dismissed
) {
    if (isOpen) {  // Show the bottom sheet if the flag is true
        ModalBottomSheet(
            sheetState = sheetState,  // Set the sheet state
            onDismissRequest = onDismissRequest,  // Handle the dismiss request
            dragHandle = {  // Customize the drag handle at the top of the bottom sheet
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()  // Default drag handle for the sheet
                    Text(text = bottomSheetTitle)  // Display the title of the bottom sheet
                    Spacer(modifier = Modifier.height(10.dp))  // Spacer for visual separation
                    Divider()  // Divider below the title
                }
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)  // Set padding for the list content
            ) {
                items(subjects) { subject ->  // Iterate over the list of subjects
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()  // Fill the width of the container
                            .clickable { onSubjectClicked(subject) }  // Handle subject click
                            .padding(8.dp)  // Add padding inside the box
                    ) {
                        Text(text = subject.name)  // Display the subject name
                    }
                }
                if (subjects.isEmpty()) {  // If no subjects are available, show a placeholder message
                    item {
                        Text(
                            modifier = Modifier.padding(10.dp),  // Add padding around the text
                            text = "Ready to begin? First, add a subject."  // Message to guide the user
                        )
                    }
                }
            }
        }
    }
}