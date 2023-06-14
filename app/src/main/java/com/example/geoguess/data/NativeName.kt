package com.example.geoguess.data

/**
 * Nazov krajiny v domacom jazyku
 */
data class NativeName(val official : String, val common : String) {
    fun getNames(): String {
        return "$official(official) or $common(common)"
    }
}
