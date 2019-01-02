package deviantart.rest

import deviantart.AUTHORS
import deviantart.GalleryItem

open class RestManager private constructor() {

    /**
     * Singleton
     * */
    private object Holder {
        val INSTANCE = RestManager()
    }

    companion object {
        val instance: RestManager by lazy { Holder.INSTANCE }
    }

    //if gallery request has no results, we will decrease total number of user arts by this percent
    private val failDecreasePercentage = 10
    private val restClient: Rest = Rest()

    fun getRandomPicFromRandomAuthor(urlCallback: (String) -> Unit) {
        val randomAuthorId = AUTHORS[getRandom(AUTHORS.size - 1)]

        getURL(randomAuthorId, urlCallback)

    }

    private fun getURL(userId: String, urlCallback: (String) -> Unit = {}) {
        restClient.getUserStat(userId,
                { userStats ->
                    val userArtsTotal = userStats.userPicturesNumber
                    val randomUserPictureNumber = getRandom(userArtsTotal)

                    getPictureURLFromUserGallery(userId, userArtsTotal, randomUserPictureNumber, urlCallback)
                },
                {
                    urlCallback(onError())
                })
    }

    fun getPictureURLFromUserGallery(userId: String, artsTotal: Int, pictureNumber: Int, urlCallback: (String) -> Unit) {

        restClient.getUserGallery(userId,
                { userGalleryResponse ->
                    if (userGalleryResponse.results.isEmpty()) {
                        val newArtRange = artsTotal - artsTotal * failDecreasePercentage / 100
                        val newPictureNumber = getRandom(newArtRange)

                        getPictureURLFromUserGallery(userId, newArtRange, newPictureNumber, urlCallback)
                    } else {
                        val pictureURL = if (userGalleryResponse.results[0].content != null) userGalleryResponse.results[0].content!!.artUrl else onError()
                        urlCallback(pictureURL)
                    }
                },
                {
                    urlCallback(onError())
                }, pictureNumber)
    }

    fun getInlineResults(query: String, offset: Int, callback: (List<GalleryItem>) -> Unit) {
        restClient.getInlineQueryResponse(query, offset,
                { galleryResponse ->
                    if (!galleryResponse.results.isEmpty()) {
                        callback(galleryResponse.results)
                    }
                })
    }


    fun onError(): String = "https://i.ytimg.com/vi/qQwm5KeGVHw/maxresdefault.jpg\nOops! Something goes wrong with me."

    fun getRandom(rangeSize: Int): Int = 0 + (Math.random() * rangeSize).toInt()
}