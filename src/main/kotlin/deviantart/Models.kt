package deviantart

import com.google.gson.annotations.SerializedName

data class GalleryResponse(@SerializedName("results") val results: List<GalleryItem>)

data class GalleryItem(val content: Content?,
                       val thumbs: List<Thumb>)

data class Content(@SerializedName("src")  val artUrl: String)

data class Thumb(@SerializedName("src") val url: String,
                 val height: Int,
                 val width: Int)

//Update Access Token
data class AccessTokenResponse(@SerializedName("access_token") val accessToken: String)


//User profile
data class UserProfileResponse(@SerializedName("stats") val statistic: UserStats)

data class UserStats(@SerializedName("user_deviations") val userPicturesNumber: Int)


//Telegram data classes
data class Update(
        val message: Message?
)

data class Message(
        val chat: Chat,
        val text: String?
)

data class Chat(
        val id: Long
)
