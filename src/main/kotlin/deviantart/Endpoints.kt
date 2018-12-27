package deviantart

private val BASE_URL = "https://www.deviantart.com/api/v1/oauth2"

val REFRESH_USER_TOKEN_URL = "https://www.deviantart.com/oauth2/token?grant_type=client_credentials&" +
        "client_id=$DEVIANT_ART_CLIENT_ID&" +
        "client_secret=$DEVIANT_ART_CLIENT_SECRET"

fun userInfo(userId: String, accessToken: String) = "$BASE_URL/user/profile/$userId?" +
        "access_token=$accessToken"

fun userGallery(userId: String, accessToken: String): String = "$BASE_URL/gallery/all?" +
        "username=$userId&access_token=$accessToken"

fun browsePopular(query: String, accessToken: String, limit: Int = 30, offset: Int = 0): String = "$BASE_URL/browse/popular?" +
        "timerange=alltime&limit=$limit&offset=$offset&q=$query&access_token=$accessToken"