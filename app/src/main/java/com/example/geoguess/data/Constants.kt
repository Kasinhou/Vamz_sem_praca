package com.example.geoguess.data

/**
 * Trieda obsahujuca konstanty programu
 */
class Constants {
    companion object {
        const val BACKGROUND_INFO_FRAGMENT = "https://e0.pxfuel.com/wallpapers/516/424/desktop-wallpaper-world-map-colors-iphone-iphone-minimalist-map.jpg"
        const val BACKGROUND_GUESSING_FRAGMENT = "https://e0.pxfuel.com/wallpapers/284/1003/desktop-wallpaper-world-map-mint-green-iphone-map-world-map-aesthetic-world.jpg"
        const val BASE_URL_ADDRESS = "https://restcountries.com/v3.1/"
        const val ADD_URL_ADDRESS = "all?fields=name,currencies,idd,capital,languages,latlng,borders,area,maps,population,fifa,timezones,continents,flags,coatOfArms"
        const val DB_NAME = "DatabaseRanking"
        const val TABLE_NAME = "Table_info"
        const val COLUMN_USER = "user"
        const val COLUMN_MIN = "min"
        const val COLUMN_SEC = "sec"
        const val COLUMN_COUNT = "count"
    }
}