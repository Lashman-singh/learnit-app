package np.com.lashman.learnit.domain.model

data class WikipediaResponse(
    val title: String,  // The title of the Wikipedia page
    val extract: String,  // A brief extract or summary from the Wikipedia page
    val thumbnail: Thumbnail?,  // Thumbnail for images (if any)
    val content_urls: ContentUrls?  // Links to related pages
)

data class Thumbnail(
    val source: String  // URL to the image for the thumbnail
)

data class ContentUrls(
    val mobile: MobileUrl  // URL for the mobile version of the Wikipedia page
)

data class MobileUrl(
    val page: String  // URL to the Wikipedia page for this topic (mobile version)
)
