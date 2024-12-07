package np.com.lashman.learnit.network

import np.com.lashman.learnit.domain.model.WikipediaResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WikipediaApiService {
    @GET("page/summary/{title}")
    suspend fun getSummary(@Path("title") title: String): WikipediaResponse
}
