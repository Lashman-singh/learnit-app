package np.com.lashman.learnit

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import np.com.lashman.learnit.presentation.NavGraphs
import np.com.lashman.learnit.presentation.destinations.SignInScreenDestination
import np.com.lashman.learnit.presentation.theme.LearnITTheme

// Annotating with @AndroidEntryPoint allows this Activity to receive dependencies injected by Hilt
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Variable to track whether the service is bound or not
    private var isBound by mutableStateOf(false)

    // Register for activity result to request notification permission
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startTimerService() // Start the timer service if permission is granted
            }
        }

    // onCreate is called when the Activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // Initialize Firebase

        // Set the content view for the Activity
        setContent {
            val navController = rememberNavController() // Create a NavController

            LearnITTheme {
                // Set up DestinationsNavHost with NavController and NavGraph
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root, // Set the root NavGraph
                    startRoute = SignInScreenDestination // Set the start route as the SignIn screen
                )
            }

            // Check authentication status and navigate if required
            LaunchedEffect(Unit) {
                checkAuthenticationStatus(navController)
            }
        }

        // Request notification permission
        requestNotificationPermission()
    }

    // Function to check if the user is authenticated and navigate to the sign-in screen if not
    private fun checkAuthenticationStatus(navController: androidx.navigation.NavController) {
        val currentUser = FirebaseAuth.getInstance().currentUser // Get the current user from FirebaseAuth
        if (currentUser == null) {
            // Navigate to the SignIn screen if the user is not authenticated
            navController.navigate(SignInScreenDestination.route) {
                popUpTo(NavGraphs.root.route) { inclusive = true } // Clear the back stack
            }
        }
    }

    // Function to request notification permission for devices running Android Tiramisu (API 33) and above
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startTimerService() // Start the timer service if permission is granted
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS) // Request permission
                }
            }
        } else {
            startTimerService() // Start the timer service if the OS version is below Android Tiramisu
        }
    }

    // Placeholder function to start the timer service (to be implemented)
    private fun startTimerService() {
        // Code to start your timer service here
    }

    // onStop is called when the Activity is no longer in the foreground
    override fun onStop() {
        super.onStop()
        // Unbind the service if it was bound (uncommented if needed)
        // unbindService(connection)
        isBound = false
    }
}
