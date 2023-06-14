package com.example.geoguess.data

/**
 * Nazvy krajin, bezne pouzivane, oficialne a domace
 */
data class Name(val common : String, val official : String, val nativeName : Map<String, NativeName>) {
    /**
     * Textova reprezentacia domacich nazvov krajin
     */
    fun getNativeNames(): String {
        var names = ""
        for ((key, value) in nativeName) {
            names += (" ▹ " + key + "-> " + value.getNames() + "\n")
        }
        return names
    }
}
