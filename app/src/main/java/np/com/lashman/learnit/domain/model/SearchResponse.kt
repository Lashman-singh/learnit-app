package np.com.lashman.learnit.domain.model

data class SearchResponse(
    val query: String,
    val results: List<SearchResult>
)

data class SearchResult(
    val title: String,
    val snippet: String,
    val pageId: Int
)
