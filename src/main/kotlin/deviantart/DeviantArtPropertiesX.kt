package deviantart

import java.io.IOException
import java.io.InputStream
import java.util.*
import


fun Properties.getClientId(): Int = getAppProperty("DEVIANT_ART_CLIENT_ID").toInt()

fun Properties.getDeviantArtSecret(): String = getAppProperty("DEVIANT_ART_CLIENT_SECRET")

fun Properties.getDeviantArtAccessToken(): String = getAppProperty("DEVIANT_ART_ACCESS_TOKEN")