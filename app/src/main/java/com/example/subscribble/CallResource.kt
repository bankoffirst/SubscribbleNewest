package com.example.subscribble
import androidx.compose.ui.graphics.Color

fun getDrawableResource(inputString: String): Int {
    return when (inputString) {
        "Netflix" -> R.drawable.netflix
        "AppleTV" -> R.drawable.appletv
        "Youtube" -> R.drawable.youtube
        "DisneyPlus" -> R.drawable.disneyplus

        "AppleMusic" -> R.drawable.applemusic
        "Spotify" -> R.drawable.spotify
        else -> R.drawable.something_went_wrong
    }
}

fun classify(inputString: String): String {
    return when (inputString) {
        "Netflix" -> "video"
        //"AppleTV" -> "video"
        "Youtube" -> "video"
        "DisneyPlus" -> "video"

        "AppleMusic" -> "music"
        "Spotify" -> "music"
        else -> ""
    }
}

fun getApplicationColor(inputString: String): Color {
    return when (inputString){
        "Netflix" -> Color(0xFFAB060F)
        //"AppleTV" -> Color(0xFF2D2D2D)
        "Youtube" -> Color(0xFFF70000)
        "DisneyPlus" -> Color(0xFF0060DE)

        "AppleMusic" -> Color(0xFFF74F6B)
        "Spotify" -> Color(0xFF1BC357)

        else -> Color.White
    }
}