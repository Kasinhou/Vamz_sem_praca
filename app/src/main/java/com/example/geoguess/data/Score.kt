package com.example.geoguess.data

/**
 * Data class na ulozenie skore jedneho uzivatela, jeho casu a poctu uhadnutych krajin
 */
data class Score(val user: String, val min: Int, val sec: Int, val count: Int)
