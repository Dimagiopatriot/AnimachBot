package deviantart.rest

import com.google.gson.Gson
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.async.Callback
import com.mashape.unirest.http.exceptions.UnirestException
import deviantart.*
import java.util.logging.Logger

class Rest {

    fun getUserGallery(userId: String, onSuccess: (GalleryResponse) -> Unit, onError: () -> Unit = {}, offset: Int = 0,
                       limit: Int = 1) {
        Unirest.get(userGallery(userId, DEVIANT_ART_ACCESS_TOKEN) + "&offset=$offset&limit=$limit")
                .asStringAsync(object : Callback<String> {

                    override fun completed(response: HttpResponse<String>) {
                        when (response.status) {
                            200 -> {
                                val gallery = Gson().fromJson(response.body, GalleryResponse::class.java)
                                onSuccess(gallery)
                            }
                            401 -> {
                                getAccessToken({ getUserGallery(userId, onSuccess, onError, offset, limit) }, onError)
                            }
                            else -> onError()
                        }
                    }

                    override fun cancelled() {
                        onError()
                    }

                    override fun failed(e: UnirestException?) {
                        onError()
                    }
                })
    }

    fun getAccessToken(onSuccess: () -> Unit, onError: () -> Unit = {}) {
        Unirest.get(REFRESH_USER_TOKEN_URL)
                .asStringAsync(object : Callback<String> {

                    override fun completed(response: HttpResponse<String>) {
                        when (response.status) {
                            200 -> {
                                val accessTokenResponse = Gson().fromJson(response.body, AccessTokenResponse::class.java)
                                DEVIANT_ART_ACCESS_TOKEN = accessTokenResponse.accessToken
                                onSuccess()
                            }
                            else -> onError()
                        }
                    }

                    override fun failed(e: UnirestException?) {
                        onError()
                    }

                    override fun cancelled() {
                        onError()
                    }
                })
    }

    fun getUserStat(userId: String, onSuccess: (UserStats) -> Unit, onError: () -> Unit = {}) {
        Unirest.get(userInfo(userId, DEVIANT_ART_ACCESS_TOKEN))
                .asStringAsync(object : Callback<String> {

                    override fun completed(response: HttpResponse<String>) {
                        when (response.status) {
                            200 -> {
                                val userProfileResponse = Gson().fromJson(response.body, UserProfileResponse::class.java)
                                onSuccess(userProfileResponse.statistic)
                            }
                            401 -> {
                                getAccessToken({ getUserStat(userId, onSuccess, onError) }, onError)
                            }
                            else -> onError()
                        }
                    }

                    override fun failed(e: UnirestException?) {
                        onError()
                    }

                    override fun cancelled() {
                        onError()
                    }
                })
    }

    fun getInlineQueryResponse(query: String, offset: Int, onSuccess: (GalleryResponse) -> Unit, onError: () -> Unit = {}) {
        val url = browsePopular(query, DEVIANT_ART_ACCESS_TOKEN, offset = offset)
        Logger.getGlobal().info("Inline query url: $url")

        val response = Unirest.get(url).asString()
        when (response.status) {
            200 -> {
                val pictureGallery = Gson().fromJson(response.body, GalleryResponse::class.java)
                onSuccess(pictureGallery)
            }
            401 -> {
                getAccessToken({ getInlineQueryResponse(query, offset, onSuccess, onError) }, onError)
            }
            else -> onError()
        }
    }
}