package np.com.lashman.learnit.domain.model

data class WikipediaResponse(
    val title: String,
    val extract: String,
    val thumbnail: Thumbnail?, // Thumbnail for images (if any)
    val content_urls: ContentUrls? // Links to related pages
)

data class Thumbnail(
    val source: String // URL to the image
)

data class ContentUrls(
    val mobile: MobileUrl
)

data class MobileUrl(
    val page: String // URL to the Wikipedia page for this topic
)
