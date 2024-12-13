package np.com.lashman.learnit.api

import np.com.lashman.learnit.domain.model.SearchResponse
import np.com.lashman.learnit.domain.model.WikipediaResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WikipediaApiService {
    @GET("page/summary/{title}")
    suspend fun getSummary(@Path("title") title: String): WikipediaResponse

    // Updated endpoint to search for related articles.
    @GET("search")
    suspend fun searchArticles(@Query("query") query: String): SearchResponse
}
