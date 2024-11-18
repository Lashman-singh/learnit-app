package np.com.lashman.learnit.presentation.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun signIn(
        email: String,
        password: String,
        onSignInSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    onSignInSuccess()
                } else {
                    onError(task.exception?.message ?: "Unknown error")
                }
            }
    }

    fun signUp(
        email: String,
        password: String,
        onSignUpSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        _isLoading.value = true
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    onSignUpSuccess()
                } else {
                    onError(task.exception?.localizedMessage ?: "Sign Up failed")
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }
}
