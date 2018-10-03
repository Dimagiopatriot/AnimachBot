package deviantart

data class UserGalleryResponse(val results: Array<GalleryItem>)

data class GalleryItem(val content: Content)

data class Content(val artUrl: String)