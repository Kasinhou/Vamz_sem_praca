package com.example.geoguess.data

data class Idd(val root : String, val suffixes : List<String>) {
    fun getIdd(): String {
        var str = "root $root"
        /*for (s in suffixes) {
            str += "$s "
        }*/
        str += "\n"
        return str
    }
}
