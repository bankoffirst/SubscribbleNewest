package com.example.subscribble
import androidx.compose.ui.graphics.Color

fun getDrawableResource(inputString: String): Int {
    return when (inputString) {
        "Netflix" -> R.drawable.netflix
        "AppleTV" -> R.drawable.appletv
        "Youtube" -> R.drawable.youtube
        "DisneyPlus" -> R.drawable.disneyplus
        "PrimeVideo" -> R.drawable.amazonprimevideo

        "AppleMusic" -> R.drawable.applemusic
        "Spotify" -> R.drawable.spotify
        "Tidal" -> R.drawable.tidal
        "JooxMusic" -> R.drawable.jooxmusic
        "SoundCloud" -> R.drawable.soundcloud
        else -> R.drawable.something_went_wrong
    }
}

fun classify(inputString: String): String {
    return when (inputString) {
        "Netflix" -> "video"
        //"AppleTV" -> "video"
        "Youtube" -> "video"
        "DisneyPlus" -> "video"
        "PrimeVideo" -> "video"

        "AppleMusic" -> "music"
        "Spotify" -> "music"
        "Tidal" -> "music"
        "JooxMusic" -> "music"
        "SoundCloud" -> "music"
        else -> ""
    }
}

fun getApplicationColor(inputString: String): Color {
    return when (inputString){
        "Netflix" -> Color(0xFFAB060F)
        //"AppleTV" -> Color(0xFF2D2D2D)
        "Youtube" -> Color(0xFFF70000)
        "DisneyPlus" -> Color(0xFF0060DE)
        "PrimeVideo" -> Color(0xFF48c6e7)

        "AppleMusic" -> Color(0xFFF74F6B)
        "Spotify" -> Color(0xFF1BC357)
        "Tidal" -> Color(0xFF040404)
        "JooxMusic" -> Color(0xFF0bdc6c)
        "SoundCloud" -> Color(0xFFF29140)

        else -> Color.White
    }
}