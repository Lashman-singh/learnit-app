package np.com.lashman.learnit.presentation.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import np.com.lashman.learnit.domain.model.WikipediaResponse
import np.com.lashman.learnit.network.RetrofitInstance

class WikipediaViewModel : ViewModel() {
    private val _summary = MutableStateFlow<WikipediaResponse?>(null)
    val summary = _summary.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun fetchSummary(title: String) {
        _loading.value = true
        _error.value = ""

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getSummary(title)
                _summary.value = response
            } catch (e: Exception) {
                _error.value = "Error fetching data from Wikipedia."
            } finally {
                _loading.value = false
            }
        }
    }
}
