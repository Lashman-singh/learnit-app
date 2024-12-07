package np.com.lashman.learnit.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import np.com.lashman.learnit.R
import np.com.lashman.learnit.presentation.destinations.DashboardScreenRouteDestination
import np.com.lashman.learnit.presentation.destinations.SignUpScreenDestination

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navigator: DestinationsNavigator) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val isLoading by authViewModel.isLoading.collectAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // If the user is already logged in, navigate to the Dashboard screen
            navigator.navigate(DashboardScreenRouteDestination()) {
                popUpTo(DashboardScreenRouteDestination.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Welcome Back", style = MaterialTheme.typography.headlineSmall) },
                colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF3E8E7E),
                            Color(0xFF1B5E20)
                        )
                    )
                )
            )
        },
        containerColor = Color(0xFFE0F7FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE0F7FA),
                            Color.White
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Image
            Image(
                painter = painterResource(id = R.drawable.learn_it_logo),
                contentDescription = "Study Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )

            // Email Input
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF00695C),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledContainerColor = Color.LightGray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF00695C),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledContainerColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sign-In Button
            Button(
                onClick = {
                    authViewModel.signIn(
                        email = email,
                        password = password,
                        onSignInSuccess = {
                            // Navigate to Dashboard on successful login
                            navigator.navigate(DashboardScreenRouteDestination()) {
                                popUpTo(DashboardScreenRouteDestination.route) { inclusive = true }
                            }
                        },
                        onError = { error -> errorMessage = error }
                    )
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00695C),
                    contentColor = Color.White
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Sign In", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Show error message if any
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign-Up Navigation
            TextButton(onClick = {
                navigator.navigate(SignUpScreenDestination())
            }) {
                Text(
                    text = "Don't have an account? Sign Up",
                    color = Color(0xFF004D40),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

//package np.com.lashman.learnit.presentation.authentication
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Button
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.google.firebase.auth.FirebaseAuth
//import com.ramcosta.composedestinations.annotation.Destination
//import com.ramcosta.composedestinations.navigation.DestinationsNavigator
//import np.com.lashman.learnit.presentation.destinations.DashboardScreenRouteDestination
//
//@Destination
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SignInScreen(
//    navigator: DestinationsNavigator
//) {
//    val authViewModel: AuthViewModel = hiltViewModel()
//    val isLoading by authViewModel.isLoading.collectAsState()
//
//    var email by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }
//    var errorMessage by rememberSaveable { mutableStateOf("") }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(title = { Text(text = "Sign In") })
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Email Input
//            TextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Password Input
//            TextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                modifier = Modifier.fillMaxWidth(),
//                visualTransformation = PasswordVisualTransformation()
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Sign-In Button
//            Button(
//                onClick = {
//                    authViewModel.signIn(
//                        email = email,
//                        password = password,
//                        onSignInSuccess = {
//                            navigator.navigate(DashboardScreenRouteDestination())
//                        },
//                        onError = { error ->
//                            errorMessage = error
//                        }
//                    )
//                },
//                enabled = !isLoading,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = if (isLoading) "Loading..." else "Sign In")
//            }
//
//            // Show error message if any
//            if (errorMessage.isNotEmpty()) {
//                Text(
//                    text = errorMessage,
//                    color = Color.Red,
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//
//            // Sign-Up Navigation (optional)
//            TextButton(onClick = {
//                // Navigate to sign-up screen
//            }) {
//                Text(text = "Don't have an account? Sign Up")
//            }
//        }
//    }
//}
