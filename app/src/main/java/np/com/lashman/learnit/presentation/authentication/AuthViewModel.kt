package np.com.lashman.learnit.presentation.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()  // FirebaseAuth instance for authentication

    private val _isLoading = MutableStateFlow(false)  // StateFlow to track loading state
    val isLoading: StateFlow<Boolean> get() = _isLoading  // Exposed as immutable StateFlow

    // Function to sign in a user with email and password
    fun signIn(
        email: String,  // Email of the user
        password: String,  // Password of the user
        onSignInSuccess: () -> Unit,  // Callback when sign-in is successful
        onError: (String) -> Unit  // Callback when an error occurs
    ) {
        _isLoading.value = true  // Set loading state to true while signing in
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false  // Set loading state to false after completion
                if (task.isSuccessful) {
                    onSignInSuccess()  // Trigger success callback
                } else {
                    onError(task.exception?.message ?: "Unknown error")  // Trigger error callback
                }
            }
    }

    // Function to sign up a new user with email and password
    fun signUp(
        email: String,  // Email of the user
        password: String,  // Password of the user
        onSignUpSuccess: () -> Unit,  // Callback when sign-up is successful
        onError: (String) -> Unit  // Callback when an error occurs
    ) {
        _isLoading.value = true  // Set loading state to true while signing up
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false  // Set loading state to false after completion
                if (task.isSuccessful) {
                    onSignUpSuccess()  // Trigger success callback
                } else {
                    onError(task.exception?.localizedMessage ?: "Sign Up failed")  // Trigger error callback
                }
            }
    }

    // Function to sign out the user
    fun signOut() {
        auth.signOut()  // Sign out the user from Firebase
    }
}
