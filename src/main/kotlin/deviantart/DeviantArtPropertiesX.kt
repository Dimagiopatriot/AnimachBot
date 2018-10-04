package deviantart

import getAppProperty
import java.util.*


fun Properties.getClientId(): Int = getAppProperty("DEVIANT_ART_CLIENT_ID").toInt()

fun Properties.getDeviantArtSecret(): String = getAppProperty("DEVIANT_ART_CLIENT_SECRET")

fun Properties.getDeviantArtAccessToken(): String = getAppProperty("DEVIANT_ART_ACCESS_TOKEN")