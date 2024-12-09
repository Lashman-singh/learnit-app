package np.com.lashman.learnit.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import np.com.lashman.learnit.domain.model.Task
import np.com.lashman.learnit.util.Priority
import np.com.lashman.learnit.util.changeMillisToDateString
import np.com.lashman.learnit.R

fun LazyListScope.tasksList(
    sectionTitle: String, // Title for the section
    emptyListText: String, // Text to display when no tasks are available
    tasks: List<Task>, // List of tasks to be displayed
    onTaskCardClick: (Int?) -> Unit, // Callback for task card click, takes task ID as argument
    onCheckBoxClick: (Task) -> Unit // Callback for checkbox click, takes task as argument
) {
    item {
        // Display the section title
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp)
        )
    }

    // Check if the task list is empty and display an empty state
    if (tasks.isEmpty()) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display an image when the task list is empty
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(R.drawable.tasks),
                    contentDescription = emptyListText
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Display a message when the task list is empty
                Text(
                    text = emptyListText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // Iterate through tasks and display each task as a TaskCard
    items(tasks) { task ->
        TaskCard(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            task = task,
            onCheckBoxClick = { onCheckBoxClick(task) }, // Handle checkbox click
            onClick = { onTaskCardClick(task.taskId) } // Handle task card click
        )
    }
}

@Composable
private fun TaskCard(
    modifier: Modifier = Modifier, // Modifier for customizing the card
    task: Task, // The task object containing the task details
    onCheckBoxClick: () -> Unit, // Callback for checkbox click
    onClick: () -> Unit // Callback for task card click
) {
    ElevatedCard(
        modifier = modifier.clickable { onClick() } // Make the card clickable
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // Row padding
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the TaskCheckBox for each task
            TaskCheckBox(
                isComplete = task.isComplete, // Checkbox state based on task completion
                borderColor = Priority.fromInt(task.priority).color, // Set border color based on task priority
                onCheckBoxClick = onCheckBoxClick // Handle checkbox click
            )
            Spacer(modifier = Modifier.width(10.dp)) // Spacer between checkbox and text
            Column {
                // Display the task title with text decoration if completed
                Text(
                    text = task.title,
                    maxLines = 1, // Limit title to one line
                    overflow = TextOverflow.Ellipsis, // Truncate title with ellipsis if it overflows
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isComplete) {
                        TextDecoration.LineThrough // Strike-through if task is complete
                    } else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(4.dp)) // Spacer between title and due date
                // Display the task's due date, formatted using a utility function
                Text(
                    text = task.dueDate.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
