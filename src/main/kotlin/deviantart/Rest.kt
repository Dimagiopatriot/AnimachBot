package deviantart

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import java.util.*

class Rest {

    fun getUserGallery(userId: String): UserGalleryResponse {
        val prop = Properties()
        val response: HttpResponse<UserGalleryResponse> = Unirest
                .get(userGallery(userId, prop.getDeviantArtAccessToken()))
                .asObject(UserGalleryResponse::class.java)
        return response.body
    }
}