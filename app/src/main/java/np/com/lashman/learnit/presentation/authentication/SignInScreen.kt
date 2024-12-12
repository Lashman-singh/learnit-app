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
    var resetEmailMessage by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sign In", style = MaterialTheme.typography.headlineSmall) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.learn_it_logo),
                contentDescription = "Study Logo",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF00695C),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF00695C),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    authViewModel.signIn(
                        email = email,
                        password = password,
                        onSignInSuccess = {
                            navigator.navigate(DashboardScreenRouteDestination()) {
                                popUpTo(DashboardScreenRouteDestination.route) { inclusive = true }
                            }
                        },
                        onError = { error -> errorMessage = error }
                    )
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
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
                    Text("Sign In", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navigator.navigate(SignUpScreenDestination())
            }) {
                Text(
                    "Don't have an account? Sign Up",
                    color = Color(0xFF004D40),
                    fontWeight = FontWeight.Medium
                )
            }

            TextButton(onClick = {
                if (email.isNotEmpty()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            resetEmailMessage = if (task.isSuccessful) {
                                "Password reset email sent."
                            } else {
                                "Failed to send password reset email."
                            }
                        }
                } else {
                    resetEmailMessage = "Please enter your email to reset password."
                }
            }) {
                Text(
                    "Forgot Password?",
                    color = Color(0xFF004D40),
                    fontWeight = FontWeight.Medium
                )
            }

            if (resetEmailMessage.isNotEmpty()) {
                Text(
                    text = resetEmailMessage,
                    color = if (resetEmailMessage == "Password reset email sent.") Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

