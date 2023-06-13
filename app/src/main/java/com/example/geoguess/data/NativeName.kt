package com.example.geoguess.data

data class NativeName(val official : String, val common : String) {
    fun getNames(): String {
        return "$official(official) or $common(common)"
    }
}
