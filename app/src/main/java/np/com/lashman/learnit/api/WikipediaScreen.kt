package np.com.lashman.learnit.presentation.api

import android.content.Context  // Import Context here
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import np.com.lashman.learnit.R
import np.com.lashman.learnit.api.WikipediaViewModel

@Destination
@Composable
fun WikipediaScreen(
    navController: NavController,
    viewModel: WikipediaViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }

    // Getting the context inside Composable function
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Wikipedia Search",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Image (if exists)
        val thumbnailUrl = viewModel.summary.collectAsState().value?.thumbnail?.source
        thumbnailUrl?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "Topic Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )
        }

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Enter topic title", color = MaterialTheme.colorScheme.onBackground) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    viewModel.fetchSummary(title)
                } else {
                    viewModel._error.value = "Please enter a title."
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00d2ff),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when {
            viewModel.loading.collectAsState().value -> {
                CircularProgressIndicator()
            }
            viewModel.error.collectAsState().value.isNotEmpty() -> {
                Text(
                    text = viewModel.error.collectAsState().value,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            else -> {
                viewModel.summary.collectAsState().value?.let { response ->
                    Text(
                        text = response.title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = response.extract,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // Related links (if any)
                    response.content_urls?.mobile?.page?.let { url ->
                        Spacer(modifier = Modifier.height(16.dp))
                        TextButton(onClick = {
                            // Navigate to the Wikipedia page in a browser
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)  // Use context to start activity
                        }) {
                            Text(text = "Read more on Wikipedia")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WikipediaScreenPreview() {
}
