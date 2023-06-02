package com.example.geoguess.data


data class Country(val flags : Flags, val coatOfArms : CoatOfArms, val name : Name, val currencies : Map<String, Currency>,
                   val idd : Idd, val capital : List<String>, val languages : Languages,
                   val latlng : List<Double>, val borders : List<String>,
                   val area : Double, val maps : Maps, val population : Int,
                   val fifa : String, val timezones : List<String>, val continents : List<String>) {


}
