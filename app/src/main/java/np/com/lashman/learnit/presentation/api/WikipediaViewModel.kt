package np.com.lashman.learnit.presentation.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import np.com.lashman.learnit.domain.model.WikipediaResponse
import np.com.lashman.learnit.network.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class WikipediaViewModel : ViewModel() {
    private val _summary = MutableStateFlow<WikipediaResponse?>(null)
    val summary = _summary.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun fetchSummary(title: String) {
        _loading.value = true
        _error.value = "" // Clear previous error

        viewModelScope.launch {
            try {
                // Ensure the title is properly encoded for the URL
                val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())

                val response = RetrofitInstance.api.getSummary(encodedTitle)

                // Check if the response contains valid data
                if (response.title.isEmpty() || response.extract.isEmpty()) {
                    _error.value = "No data available for this topic."
                } else {
                    _summary.value = response
                }
            } catch (e: HttpException) {
                // Handle HTTP error, e.g., 404 not found
                if (e.code() == 404) {
                    _error.value = "The page does not exist."
                } else {
                    _error.value = "Error fetching data from Wikipedia. Please try again."
                }
            } catch (e: IOException) {
                // Handle network errors
                _error.value = "Network error. Please check your connection."
            } finally {
                _loading.value = false
            }
        }
    }
}
