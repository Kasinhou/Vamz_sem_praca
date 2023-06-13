package com.example.geoguess.data


data class Country(val flags : Flags, val coatOfArms : CoatOfArms, val name : Name, val currencies : Map<String, Currency>,
                   val idd : Idd, val capital : List<String>, val languages : Map<String, String>,
                   val latlng : List<Double>, val borders : List<String>,
                   val area : Double, val maps : Maps, val population : Int,
                   val fifa : String, val timezones : List<String>, val continents : List<String>) {

    fun showInfo(): String {
        var capitals = ""
        for (n in capital) {
            capitals += ("$n   ")
        }

        var bor = ""
        for (c in borders) {
            bor += ("$c ")
        }

        var lang = ""
        for ((key, value) in languages) {
            lang += ("$value ")
        }

        var curr = ""
        for ((key, value) in currencies) {
            curr += (" ${value.getCurrency()} $key\n")
        }

        var zones = ""
        for (z in timezones) {
            zones += ("$z ")
        }

        var conts = ""
        for (c in continents) {
            conts += ("$c  ")
        }

        return "OFFICIAL NAME: ${name.official}\nNATIVE NAME:\n${name.getNativeNames()}CAPITALS: $capitals\n" +
                "CODE: $fifa\nPOPULATION: ${population.toString()}\nAREA: ${area.toString()}\nBORDERS:\n $bor\nLANGUAGES:\n $lang\n" +
                "CURRENCIES:\n${curr}LATITUDE AND LONGITUDE: ${latlng[0].toString()}, ${latlng[1].toString()}\nIDD: ${idd.root}\nTIMEZONES:\n $zones\nCONTINENTS: $conts"
    }


}
