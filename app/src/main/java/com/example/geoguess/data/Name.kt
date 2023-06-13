package com.example.geoguess.data

data class Name(val common : String, val official : String, val nativeName : Map<String, NativeName>) {
    fun getNativeNames(): String {
        var names = ""
        //names += nativeName.keys.first() + "- " + nativeName.values.first().getNames()
        for ((key, value) in nativeName) {
            names += (" ▹ " + key + "-> " + value.getNames() + "\n")
        }
        return names
    }
}
