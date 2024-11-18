//package np.com.lashman.learnit
//
//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.content.ServiceConnection
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Bundle
//import android.os.IBinder
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.core.app.ActivityCompat
//import com.ramcosta.composedestinations.DestinationsNavHost
//import com.ramcosta.composedestinations.navigation.dependency
//import dagger.hilt.android.AndroidEntryPoint
//import np.com.lashman.learnit.presentation.NavGraphs
//import np.com.lashman.learnit.presentation.destinations.SessionScreenRouteDestination
//import np.com.lashman.learnit.presentation.session.StudySessionTimerService
//import np.com.lashman.learnit.presentation.theme.LearnITTheme
//
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//
//    private var isBound by mutableStateOf(false)
//    private lateinit var timerService: StudySessionTimerService
//    private val connection = object : ServiceConnection {
//        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
//            val binder = service as StudySessionTimerService.StudySessionTimerBinder
//            timerService = binder.getService()
//            isBound = true
//        }
//
//        override fun onServiceDisconnected(p0: ComponentName?) {
//            isBound = false
//        }
//    }
//
//    // Use the ActivityResultLauncher for permission requests
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            if (isGranted) {
//                // Permission is granted, you can now start your foreground service if necessary
//                startTimerService()
//            } else {
//                // Handle permission denial if necessary
//            }
//        }
//
//    override fun onStart() {
//        super.onStart()
//        Intent(this, StudySessionTimerService::class.java).also { intent ->
//            bindService(intent, connection, Context.BIND_AUTO_CREATE)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            if (isBound) {
//                LearnITTheme {
//                    DestinationsNavHost(
//                        navGraph = NavGraphs.root,
//                        dependenciesContainerBuilder = {
//                            dependency(SessionScreenRouteDestination) { timerService }
//                        }
//                    )
//                }
//            }
//        }
//        requestNotificationPermission()
//    }
//
//    private fun requestNotificationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            when {
//                ActivityCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.POST_NOTIFICATIONS
//                ) == PackageManager.PERMISSION_GRANTED -> {
//                    startTimerService()
//                }
//                else -> {
//                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                }
//            }
//        } else {
//            // For API levels below 33, you may not need to request this permission
//            startTimerService()
//        }
//    }
//
//    private fun startTimerService() {
//        // Code to start your timer service here, if necessary
//    }
//
//    override fun onStop() {
//        super.onStop()
//        unbindService(connection)
//        isBound = false
//    }
//}

//package np.com.lashman.learnit
//
//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.content.ServiceConnection
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Bundle
//import android.os.IBinder
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.core.app.ActivityCompat
//import com.ramcosta.composedestinations.DestinationsNavHost
//import com.ramcosta.composedestinations.navigation.dependency
//import dagger.hilt.android.AndroidEntryPoint
//import np.com.lashman.learnit.presentation.NavGraphs
//import np.com.lashman.learnit.presentation.destinations.SessionScreenRouteDestination
//import np.com.lashman.learnit.presentation.session.StudySessionTimerService
//import np.com.lashman.learnit.presentation.theme.LearnITTheme
//
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//
//    private var isBound by mutableStateOf(false)
//    private lateinit var timerService: StudySessionTimerService
//    private val connection = object : ServiceConnection {
//        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
//            val binder = service as StudySessionTimerService.StudySessionTimerBinder
//            timerService = binder.getService()
//            isBound = true
//        }
//
//        override fun onServiceDisconnected(p0: ComponentName?) {
//            isBound = false
//        }
//    }
//
//    // Use the ActivityResultLauncher for permission requests
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            if (isGranted) {
//                // Permission is granted, you can now start your foreground service if necessary
//                startTimerService()
//            } else {
//                // Handle permission denial if necessary
//            }
//        }
//
//    override fun onStart() {
//        super.onStart()
//        Intent(this, StudySessionTimerService::class.java).also { intent ->
//            bindService(intent, connection, Context.BIND_AUTO_CREATE)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            if (isBound) {
//                LearnITTheme {
//                    DestinationsNavHost(
//                        navGraph = NavGraphs.root,
//                        dependenciesContainerBuilder = {
//                            dependency(SessionScreenRouteDestination) { timerService }
//                        }
//                    )
//                }
//            }
//        }
//        requestNotificationPermission()
//    }
//
//    private fun requestNotificationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            when {
//                ActivityCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.POST_NOTIFICATIONS
//                ) == PackageManager.PERMISSION_GRANTED -> {
//                    startTimerService()
//                }
//                else -> {
//                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                }
//            }
//        } else {
//            // For API levels below 33, you may not need to request this permission
//            startTimerService()
//        }
//    }
//
//    private fun startTimerService() {
//        // Code to start your timer service here, if necessary
//    }
//
//    override fun onStop() {
//        super.onStop()
//        unbindService(connection)
//        isBound = false
//    }
//}

package np.com.lashman.learnit

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import np.com.lashman.learnit.presentation.NavGraphs
import np.com.lashman.learnit.presentation.destinations.SignInScreenDestination
//import np.com.lashman.learnit.presentation.destinations.SignInScreenDestination
import np.com.lashman.learnit.presentation.session.StudySessionTimerService
import np.com.lashman.learnit.presentation.theme.LearnITTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isBound by mutableStateOf(false)
    private lateinit var timerService: StudySessionTimerService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val binder = service as StudySessionTimerService.StudySessionTimerBinder
            timerService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startTimerService()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            val navController = rememberNavController()

            LearnITTheme {
                // Set up DestinationsNavHost with NavController and NavGraph
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root,
                    startRoute = SignInScreenDestination, // Set the start route here
                    dependenciesContainerBuilder = {
                        dependency(NavGraphs.root) { timerService }
                    }
                )
            }

            // Check authentication status and navigate if required
            LaunchedEffect(Unit) {
                checkAuthenticationStatus(navController)
            }
        }

        requestNotificationPermission()
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StudySessionTimerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun checkAuthenticationStatus(navController: androidx.navigation.NavController) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            navController.navigate(SignInScreenDestination.route) {
                popUpTo(NavGraphs.root.route) { inclusive = true }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startTimerService()
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            startTimerService()
        }
    }

    private fun startTimerService() {
        // Code to start your timer service here
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}
